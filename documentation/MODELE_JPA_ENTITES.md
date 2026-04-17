# Modèle JPA des Entités - Gestion de Visa

**Date:** 16 avril 2026  
**Version:** 1.0

## Vue d'ensemble du modèle

Ce document décrit la stratégie et le modèle appliqué pour chaque entité JPA du projet VisaManagement, tirée du script SQL.

### Conventions générales

- **Package:** `com.itu.visamanagement.entity`
- **Générateur de clés:** `GenerationType.IDENTITY` (PostgreSQL `SERIAL`)
- **Fetch Strategy:** `FetchType.LAZY` pour les relations (optimisation performance)
- **Pas de Lombok:** Getters/setters générés manuellement
- **Noms de colonnes SQL:** Préservés explicitement avec `@Column(name = "...")`
- **DateAPI:** `java.time.LocalDate` pour les colonnes SQL `DATE`

---

## Entités de "lookup" / Référentielles

### 1. **Pays**
- **Table SQL:** `Pays`
- **Colonnes:** `Id_Pays` (PK, SERIAL), `libelle` (VARCHAR 50)
- **Relations:** Référencée par `EtatCivil` (ManyToOne)
- **Modèle appliqué:**
  - Clé primaire auto-générée avec `@GeneratedValue(GenerationType.IDENTITY)`
  - Attribut simple `libelle` sans validation supplémentaire
  - Pas de `@OneToMany` car la relation est unidirectionnelle depuis `EtatCivil`
- **Cas d'usage:** Liste de pays possibles pour un demandeur

### 2. **Nationalite**
- **Table SQL:** `nationalite`
- **Colonnes:** `Id_nationalite` (PK, SERIAL), `libelle` (VARCHAR 50)
- **Relations:** Référencée par `EtatCivil` (ManyToOne)
- **Modèle appliqué:**
  - Clé primaire auto-générée
  - Attribut `libelle` simple
  - Structure identique à `Pays`
- **Cas d'usage:** Nationalité d'un demandeur

### 3. **Sexe**
- **Table SQL:** `sexe`
- **Colonnes:** `Id_sexe` (PK, SERIAL), `libelle` (VARCHAR 50)
- **Relations:** Référencée par `EtatCivil` (ManyToOne)
- **Modèle appliqué:** Identique aux entités de lookup (`Pays`, `Nationalite`)
- **Cas d'usage:** Sexe du demandeur (M/F)

### 4. **SituationFamiliale**
- **Table SQL:** `situation_familiale`
- **Colonnes:** `Id_situation_familiale` (PK, SERIAL), `libelle` (VARCHAR 50)
- **Relations:** Référencée par `EtatCivil` (ManyToOne)
- **Modèle appliqué:** Identique aux autres lookup tables
- **Cas d'usage:** Situation familiale (marié, célibataire, etc.)

### 5. **TypeDemandeVisa**
- **Table SQL:** `type_demande_visa`
- **Colonnes:** `Id_type_demande_visa` (PK, SERIAL), `libelle` (VARCHAR 50)
- **Relations:** Référencée par `Demandeur` (ManyToOne)
- **Modèle appliqué:** Identique aux lookup tables
- **Cas d'usage:** Type de demande (nouveau, renouvellement, etc.)

### 6. **TypeVisa**
- **Table SQL:** `type_visa`
- **Colonnes:** `Id_type_visa` (PK, SERIAL), `libelle` (VARCHAR 50)
- **Relations:**
  - Référencée par `Demandeur` (OneToMany bidirectionnel)
  - Participe à table de junction `TypeVisaDocuments`
- **Modèle appliqué:**
  ```java
  @OneToMany(mappedBy = "typeVisa", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Demandeur> demandeurs;
  ```
  - L'attribut `demandeurs` est un commentaire de la relation inverse
  - `cascade = CascadeType.ALL` : supprimer `TypeVisa` supprime ses `Demandeur`
  - `orphanRemoval = true` : supprime les `Demandeur` non référencés
- **Cas d'usage:** Type de visa (tourisme, travail, étudiant, etc.)

### 7. **DocumentsCommuns**
- **Table SQL:** `documents_communs`
- **Colonnes:** `Id_documents_commune` (PK, SERIAL), `libelle` (VARCHAR 50)
- **Relations:** Référencée par `DemandeurDocumentsCommuns` (OneToMany implicite)
- **Modèle appliqué:** Lookup table simple, sans liste inverse explicite
- **Cas d'usage:** Documents obligatoires pour tous les demandeurs

### 8. **DocumentsTypes**
- **Table SQL:** `documents_types`
- **Colonnes:** `Id_documents_types` (PK, SERIAL), `libelle` (VARCHAR 50)
- **Relations:** Référencée par `DemandeurDocumentsTypes` (OneToMany implicite)
- **Modèle appliqué:** Lookup table simple
- **Cas d'usage:** Documents spécifiques au type de visa demandé

---

## Entités métier principales

### 9. **Demandeur**
- **Table SQL:** `demandeur`
- **Colonnes:**
  - `Id_demandeur` (PK, SERIAL)
  - `code` (VARCHAR 50)
  - `Id_type_visa` (FK, NOT NULL)
  - `Id_type_demande_visa` (FK, NOT NULL)
- **Relations:**
  - `ManyToOne` → `TypeVisa`
  - `ManyToOne` → `TypeDemandeVisa`
  - `OneToOne` → `EtatCivil` (bidirectionnel)
  - `OneToMany` → `DemandeurDocumentsTypes` (bidirectionnel)
  - `OneToMany` → `DemandeurDocumentsCommuns` (bidirectionnel)
- **Modèle appliqué:**
  ```java
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Id_type_visa", nullable = false)
  private TypeVisa typeVisa;

  @OneToOne(mappedBy = "demandeur", cascade = CascadeType.ALL, orphanRemoval = true)
  private EtatCivil etatCivil;
  ```
  - Relations `ManyToOne` : lazy loading (ne pas charger les references)
  - Relation `OneToOne` inverse : cascade complète pour préserver intégrité
- **Cas d'usage:** Entité centrale, représente un candidat à un visa

### 10. **EtatCivil**
- **Table SQL:** `etat_civil`
- **Colonnes:**
  - `Id_etat_civil` (PK, SERIAL)
  - `nom`, `prenom`, `nom_jeune_fille`, `date_naissance`, `lieu_naissance`
  - `email`, `contact`
  - `Id_situation_familiale`, `Id_sexe`, `Id_nationalite`, `Id_Pays`, `Id_demandeur` (FKs, NOT NULL)
- **Relations:**
  - `ManyToOne` → `SituationFamiliale`, `Sexe`, `Nationalite`, `Pays` (4 relations lookup)
  - `OneToOne` → `Demandeur` (bidirectionnel, UNIQUE constraint)
  - `OneToOne` inverse → `Passeport`, `VisaTransformable` (bidirectionnels)
- **Modèle appliqué:**
  ```java
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Id_demandeur", nullable = false, unique = true)
  private Demandeur demandeur;

  @OneToOne(mappedBy = "etatCivil", cascade = CascadeType.ALL, orphanRemoval = true)
  private Passeport passeport;
  ```
  - La relation directe (`@JoinColumn`) est sur `EtatCivil` (côté "enfant")
  - Les relations inverses (`mappedBy`) sont sur `Passeport` et `VisaTransformable`
  - `LocalDate` pour les colonnes `DATE` SQL
- **Cas d'usage:** Informations civiles d'une personne demandant un visa

### 11. **Passeport**
- **Table SQL:** `passeport`
- **Colonnes:**
  - `Id_passeport` (PK, SERIAL)
  - `numero`, `date_delivrance`, `date_expiration`
  - `Id_etat_civil` (FK, NOT NULL, UNIQUE)
- **Relations:**
  - `OneToOne` ← `EtatCivil` (inverse, cascade)
- **Modèle appliqué:**
  ```java
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Id_etat_civil", nullable = false, unique = true)
  private EtatCivil etatCivil;
  ```
  - `unique = true` dans `@JoinColumn` reflète le constraint SQL `UNIQUE(Id_etat_civil)`
  - Lazy loading pour ne pas charger `EtatCivil` systématiquement
- **Cas d'usage:** Informations du passeport d'un demandeur (1 passeport par personne max)

### 12. **VisaTransformable**
- **Table SQL:** `visa_transformable`
- **Colonnes:**
  - `Id_visa_transformable` (PK, SERIAL)
  - `reference`, `date_entree_mada`, `lieu`, `date_expiration`
  - `Id_etat_civil` (FK, NOT NULL, UNIQUE)
- **Relations:**
  - `OneToOne` ← `EtatCivil` (inverse, cascade)
- **Modèle appliqué:** Identique à `Passeport`
- **Cas d'usage:** Visa existant transformable en un autre type de visa

---

## Entités d'association (tables de junction)

### 13. **DemandeurDocumentsTypes**
- **Table SQL:** `demandeur_documents_types`
- **Colonnes:**
  - `Id_demandeur_documents` (PK, SERIAL)
  - `is_ok` (BOOLEAN)
  - `Id_documents_types`, `Id_demandeur` (FKs, NOT NULL)
- **Relations:**
  - `ManyToOne` → `DocumentsTypes`
  - `ManyToOne` → `Demandeur`
- **Modèle appliqué:**
  ```java
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Id_documents_types", nullable = false)
  private DocumentsTypes documentsTypes;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Id_demandeur", nullable = false)
  private Demandeur demandeur;
  ```
  - Entité d'association avec attribut métier (`is_ok`)
  - Liens bidirectionnels : `Demandeur` contient `List<DemandeurDocumentsTypes>`
  - Lazy loading sur les deux relations
- **Cas d'usage:** Suivi des documents spécifiques requis pour un demandeur et leur statut

### 14. **DemandeurDocumentsCommuns**
- **Table SQL:** `demandeur_documents_communs`
- **Colonnes:**
  - `Id_demandeur_documents_communs` (PK, SERIAL)
  - `is_ok` (BOOLEAN)
  - `Id_demandeur`, `Id_documents_commune` (FKs, NOT NULL)
- **Relations:**
  - `ManyToOne` → `Demandeur`
  - `ManyToOne` → `DocumentsCommuns`
- **Modèle appliqué:** Identique à `DemandeurDocumentsTypes`
- **Cas d'usage:** Suivi des documents communs obligatoires pour tous les demandeurs

### Note sur la table `type_visa_documents`

La table `type_visa_documents` est une table de junction pure (clé composée, pas d'entité JPA dédiée).  
Elle n'a pas encore d'entité JPA créée. Si nécessaire, elle peut être :
- Mappée via une entité d'association (comme `DemandeurDocumentsTypes`)
- Gérée implicitement via `@ManyToMany` avec `join table`

Cette décision sera revisitée selon les besoins de l'application.

---

## Bonnes pratiques appliquées

1. **Pas de Lombok:** Getters/setters écrits explicitement pour plus de contrôle
2. **Lazy Loading:** `FetchType.LAZY` par défaut pour éviter les requêtes N+1
3. **Cascade:** `CascadeType.ALL` + `orphanRemoval = true` pour les relations `OneToOne`/`OneToMany` fortes
4. **Noms SQL explicites:** Chaque colonne a un `@Column(name = "...")` qui respecte le schéma SQL
5. **DateAPI:** `java.time.LocalDate` plutôt que `java.util.Date`
6. **Constructors:** Constructeurs vides (requis par JPA) + constructeurs avec paramètres essentiels
7. **toString():** Implémentés pour faciliter le debug (sans charger les relations)

---

## Diagram de relations (synthèse)

```
┌─────────────────────┐
│  Pays / Nationalite │
│  Sexe / ...         │ (Lookup tables)
└──────────┬──────────┘
           │ ManyToOne
           │
┌──────────▼──────────┐
│   EtatCivil         │
│ (Infos civiles)     │
├─────────────────────┤
│ OneToOne ←→ Demandeur
│ OneToOne ←→ Passeport
│ OneToOne ←→ VisaTransformable
└──────────┬──────────┘
           │
           ├─→ Demandeur
           │   ├── ManyToOne → TypeVisa
           │   ├── ManyToOne → TypeDemandeVisa
           │   ├── OneToMany → DemandeurDocumentsTypes
           │   └── OneToMany → DemandeurDocumentsCommuns
           │
           └─→ Passeport / VisaTransformable
               (OneToOne inverse)
```

---

## Annotations clés utilisées

| Annotation | Usage |
|-----------|-------|
| `@Entity` | Déclaration d'entité JPA |
| `@Table(name = "...")` | Mapping à la table SQL |
| `@Column(name = "...", length = 50)` | Mapping des colonnes SQL |
| `@Id` | Clé primaire |
| `@GeneratedValue(GenerationType.IDENTITY)` | Auto-incrémentation PostgreSQL |
| `@ManyToOne`, `@OneToMany`, `@OneToOne` | Types de relations |
| `@JoinColumn(name = "...", nullable = false)` | Mapping FK, contraintes |
| `@FetchType.LAZY` | Chargement différé (défaut recommandé) |
| `@CascadeType.ALL`, `orphanRemoval = true` | Gestion du cycle de vie |
| `@OneToOne(mappedBy = "...")` | Côté inverse d'une relation OneToOne |

---

**Généré le:** 16 avril 2026  
**Auteur:** Génération automatisée JPA
