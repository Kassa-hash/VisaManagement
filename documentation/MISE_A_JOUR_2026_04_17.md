# Mise à Jour Conception et Implémentation - 17/04/2026

## 🎯 Objectif
Adapter la conception et l'implémentation Spring Boot au nouveau schéma SQL révisé de la application de gestion des visas.

---

## 📋 Changements Majeurs du Schéma

### Nouvelles Tables/Entités
| Entité | Rôle | Champs Clés |
|--------|------|------------|
| **Statut** | Gestion des statuts | Id, libelle |
| **Demande** | Demande de visa (central) | Id, date_demande, FK typeVisa, FK demandeur, FK statut, FK typeDemandeVisa |
| **Visa** | Visa accordé | Id, date_debut, date_fin, FK demande |
| **CarteResident** | Carte de résident | Id, date_debut, date_fin, FK passeport, FK demande |

### Tables Modifiées
| Entité | Modifications |
|--------|----------------|
| **Demandeur** | Simplifié: juste `id` + `code`, perd les FK directes vers TypeVisa/TypeDemandeVisa |
| **EtatCivil** | Perte OneToOne vers Passeport/VisaTransformable, ajout colonne `adresse_mada` |
| **Passeport** | FK change de EtatCivil → Demandeur |
| **VisaTransformable** | FK change de EtatCivil → Demandeur + Passeport |

### Tables Supprimées (remplacées)
- `demandeur_documents_types` → `demande_documents_types`
- `demandeur_documents_communs` → `demande_documents_communs`

---

## 🔧 Entités JPA Créées/Modifiées

### Nouvelles Entités ✅
```
src/main/java/com/itu/visamanagement/entity/
├── Statut.java (NEW)
├── Demande.java (NEW) 
├── Visa.java (NEW)
├── CarteResident.java (NEW)
├── DemandeDocumentsTypes.java (NEW)
└── DemandeDocumentsCommuns.java (NEW)
```

### Entités Modifiées ✅
```
├── Demandeur.java (simplifié, OneToMany Passeport/VisaTransformable/Demande)
├── EtatCivil.java (ajout adresseMada, suppression OneToOne Passeport/VisaTransformable)
├── Passeport.java (FK vers Demandeur)
└── VisaTransformable.java (FK vers Demandeur + Passeport)
```

---

## 🗄️ Repositories

### Nouveaux Repositories ✅
```
src/main/java/com/itu/visamanagement/repository/
├── StatutRepository.java
├── DemandeRepository.java
├── VisaRepository.java
├── CarteResidentRepository.java
├── DemandeDocumentsTypesRepository.java
└── DemandeDocumentsCommunsRepository.java
```

---

## 🎁 Service & Controller

### DemandeurService.java ✅
**Changements:**
- Nouvelle méthode: `createDemande(DemandeurDTO)` remplace `createDemandeur(DemandeurDTO)`
- Injection des nouveaux repositories (DemandeRepository, StatutRepository, etc.)
- Flux: Crée Demandeur → EtatCivil → Passeport → VisaTransformable → **Demande** → DemandeDocuments
- Retourne le `String code` généré (au lieu de l'objet Demandeur)

### DemandeurController.java ✅
**Changements:**
- Méthode POST appelle `demandeurService.createDemande(dto)` 
- Récupère le code retourné et redirige vers `/demande/success?code=CODE`

### DemandeurDTO.java ✅
**Changements:**
- Ajout du champ `adresseMada` (optionnel)
- Getters/Setters ajoutés

---

## 🎨 Template Thymeleaf

### demande-form.html ✅
**Changements:**
- Ajout du champ `adresseMada` dans Étape 1 (État civil)
- Champ: input type="text", optionnel, pour "Adresse à Madagascar"

---

## 📊 Données Initiales (Script SQL)

### insert-lookup-data.sql ✅
New file contenant tous les INSERTs pour:
- **sexe** (2 lignes): Masculin, Féminin
- **nationalite** (7 lignes): Malagasy, Français, Américain, etc.
- **pays** (9 lignes): Madagascar, France, États-Unis, etc.
- **situation_familiale** (5 lignes): Célibataire, Marié(e), etc.
- **type_visa** (5 lignes): Visa Investisseur, Travailleur, Étudiant, etc.
- **type_demande_visa** (3 lignes): Première demande, Renouvellement, Transformation
- **documents_communs** (7 lignes): Passeport, Extrait naissance, etc.
- **documents_types** (9 lignes): Certificat scolarité, Contrat travail, etc.
- **statut** (5 lignes): En attente, Acceptée, Refusée, Suspendue, En cours

**À exécuter:** 
```bash
psql -U user -d visa_management < insert-lookup-data.sql
```

---

## 🔗 Flux Complet Revisité

```
1. GET /demande
   → Affiche form vide + popule dropdowns (getLookupData())

2. POST /demande (submit form)
   → DemandeurService.createDemande(DTO)
   → Crée: Demandeur(code) → EtatCivil → Passeport → VisaTransformable → Demande → DemandeDocuments
   → @Transactional assure l'atomicité
   → Retourne code généré (ex: D-2026-000001)

3. GET /demande/success?code=D-2026-000001
   → Affiche page de confirmation avec le code
```

---

## ⚠️ Données Attendues dans BD

**Avant de tester l'appli, exécuter:**
```sql
-- Insérer données lookup
psql -U your_user -d visa_management_db < base/script/insert-lookup-data.sql
```

Sinon, les dropdowns seront vides et le formulaire ne pourra pas être complété.

---

## ✅ Checklist Pré-Test

- [x] Entités JPA créées et modifiées
- [x] Repositories créés
- [x] Service refactorisé
- [x] Controller mis à jour
- [x] DTO avec adresseMada
- [x] Template avec champ adresseMada
- [x] Script SQL d'insertion
- [ ] `mvn clean compile` — **À faire**
- [ ] Initialiser BD avec script SQL
- [ ] Tester GET /demande
- [ ] Tester POST /demande avec données complètes
- [ ] Vérifier redirection vers /demande/success
- [ ] Checker données insérées en BD

---

## 📝 Notes Importantes

### Relations JPA
```
Demandeur
├── OneToOne → EtatCivil (mapped by demandeur)
├── OneToMany → Passeport (mapped by demandeur)
├── OneToMany → VisaTransformable (mapped by demandeur)  
└── OneToMany → Demande (mapped by demandeur)

Demande
├── ManyToOne → Demandeur
├── ManyToOne → TypeVisa
├── ManyToOne → TypeDemandeVisa
├── ManyToOne → Statut
├── OneToMany → DemandeDocumentsTypes
└── OneToMany → DemandeDocumentsCommuns
```

### Points d'attention
1. **Statut initial**: Service utilise statut avec id=1 (ajouter à la BD: "En attente")
2. **Code unique**: Format `D-YYYY-######` généré automatiquement
3. **Identité composite**: Les clés primaires sont SERIAL (auto-increment)
4. **Cascade**: Tous les OneToMany/OneToOne ont CascadeType.ALL + orphanRemoval=true

---

## 🚀 Prochaines Étapes

1. **Compilation**: `mvn clean compile` pour vérifier qu'il n'y a pas d'erreurs d'imports
2. **Base de données**: Exécuter script SQL pour insérer données de lookup
3. **Démarrage**: `mvn spring-boot:run` ou lancer depuis IDE
4. **Test manuel**: Remplir formulaire et vérifier créat

ion en BD
5. **Hooks supplémentaires**: Ajouter pagination pour les listes, recherche, etc. si besoin
