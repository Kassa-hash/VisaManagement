# Résolution du Problème de Dropdowns Vides

## 🔍 Diagnostic

Le problème de dropdowns qui n'affichent pas les données correctement est dû à **l'absence de données dans les lookup tables.**

### Cause Principale
Les tables `sexe`, `nationalite`, `pays`, `situation_familiale`, `type_visa`, `type_demande_visa`, `documents_communs`, `documents_types`, `statut` ne contiennent PAS de données.

### Flux Affecté
```
1. GET /demande
   → DemandeurController.showForm()
   → demandeurService.getLookupData()
   → sexeRepository.findAll()  // ← Retourne une liste VIDE
   → th:each="sexe : ${sexes}"  // ← Rien à itérer, select vide
```

---

## ✅ Solution

### Étape 1: Exécuter le script SQL d'insertion

```bash
# Option 1: Via psql directement
psql -U votre_user -d vista_management_db < base/script/insert-lookup-data.sql

# Option 2: Copier-coller dans pgAdmin ou autre client SQL
# Ouvrir: base/script/insert-lookup-data.sql
# Exécuter tout le contenu
```

### Étape 2: Vérifier les données insérées

```sql
-- Vérifier dans pgAdmin ou psql:
SELECT * FROM sexe;           -- Doit retourner 2 lignes
SELECT * FROM pays;           -- Doit retourner 9 lignes
SELECT * FROM statut;         -- Doit retourner 5 lignes
-- etc.
```

### Étape 3: Redémarrer l'application
```bash
mvn spring-boot:run
```

### Étape 4: Naviguer vers `/demande`
Les dropdowns doivent maintenant afficher les données.

---

## 📊 Données à Insérer (Résumé)

| Table | Nombre de lignes | Exemples |
|-------|-----------------|----------|
| `sexe` | 2 | Masculin, Féminin |
| `nationalite` | 7 | Malagasy, Français, Américain, Britannique, Allemand, Chinois, Indien |
| `pays` | 9 | Madagascar, France, États-Unis, Royaume-Uni, Allemagne, Chine, Inde, Japon, Afrique du Sud |
| `situation_familiale` | 5 | Célibataire, Marié, Divorcé, Veuf/Veuve, Pacsé |
| `type_visa` | 5 | Visa Investisseur, Visa Travailleur, Visa Étudiant, Visa Touristique, Visa Familial |
| `type_demande_visa` | 3 | Première demande, Renouvellement, Transformation |
| `documents_communs` | 7 | Extrait naissance, Certificat mariage, Passeport, Carte identité, etc. |
| `documents_types` | 9 | Certificat scolarité, Contrat travail, Justificatif revenus, Assurance maladie, etc. |
| `statut` | 5 | En attente, Acceptée, Refusée, Suspendue, En cours de traitement |

**Total: ~52 lignes d'insertion**

---

## 🔧 Alternative: Insert via Spring Boot

Si vous préférez, créez un composant pour initialiser les données au démarrage:

```java
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    
    private final SexeRepository sexeRepository;
    private final PaysRepository paysRepository;
    // ... autres repos ...
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (sexeRepository.count() == 0) {
            // Insert sexes
            sexeRepository.saveAll(List.of(
                new Sexe("Masculin"),
                new Sexe("Féminin")
            ));
            // ... autres inserts ...
        }
    }
}
```

---

## 📌 Checklist

- [ ] Script `insert-lookup-data.sql` exécuté avec succès
- [ ] Vérifier les données en BD (SELECT * FROM [table])
- [ ] Redémarrer Spring Boot
- [ ] Naviguer vers `/demande`
- [ ] Vérifier que les dropdowns ne sont plus vides
- [ ] Remplir et soumettre un formulaire test

---

## 🚨 Si les dropdowns sont TOUJOURS vides

1. **Vérifier la connexion DB**: `application.properties` correct?
2. **Vérifier les données**: Exécuter les SELECT dans la section ci-dessus
3. **Vérifier les repos**: S'assurer que les repositories héritent de `JpaRepository`
4. **Logs Spring**: Regarder `mvn spring-boot:run` pour les erreurs de requête
5. **Cache Navigateur**: Effacer le cache ou ouvrir une session privée

---

## 📄 Fichier Script

**Localisation**: `base/script/insert-lookup-data.sql`

**Contenu**: Tous les INSERT pour les lookup tables

**À utiliser après** la création de la BD via JPA (Hibernate).
