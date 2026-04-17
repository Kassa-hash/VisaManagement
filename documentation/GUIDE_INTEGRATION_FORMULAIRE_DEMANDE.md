# Guide d'intégration — Formulaire Demande de Visa

**Date:** 16 avril 2026  
**Objectif:** Transformer la page HTML statique `Demande_form.html` en formulaire dynamique intégré avec Spring Boot (pattern Entity → Repository → Service → Controller)

---

## Vue d'ensemble du processus

```
┌─────────────────────────────────────────────────────────────┐
│   Utilisateur remplit le formulaire multi-étapes            │
│   (Demande_form.html adapté en Thymeleaf)                   │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ▼
        ┌────────────────────────┐
        │  Controller            │
        │ DemandeurController    │
        │ - GET /demande         │ ← Affiche le formulaire
        │ - POST /demande        │ ← Reçoit et valide
        └────────────┬───────────┘
                     │
                     ▼
        ┌────────────────────────┐
        │  Service              │
        │ DemandeurService      │
        │ - createDemandeur()   │ ← Logique métier
        │ - populateLookups()   │ ← Charge Sexe, Pays...
        └────────────┬───────────┘
                     │
                     ▼
        ┌────────────────────────┐
        │  Repository           │
        │ DemandeurRepository   │
        │ - save()              │ ← Persiste dans BD
        └────────────┬───────────┘
                     │
                     ▼
        ┌────────────────────────┐
        │  PostgreSQL Database   │
        │ Sauvegarde la demande  │
        └────────────────────────┘
```

---

## Étape 1 : Adapter la page HTML en template Thymeleaf

### Fichier: `src/main/resources/templates/demande-form.html`

**Actions:**
1. Remplacer les IDs de select statiques par Thymeleaf
2. Ajouter binding du formulaire à un objet DTO
3. Ajouter gestion des erreurs de validation

**Exemple de transformation — Étape 1 (État civil):**

```html
<!-- AVANT (HTML statique) -->
<select id="sexe" name="Id_sexe">
    <option value="">-- Sélectionner --</option>
    <option value="1">Masculin</option>
    <option value="2">Féminin</option>
</select>

<!-- APRÈS (Thymeleaf dynamique) -->
<select id="sexe" name="idSexe" th:field="*{idSexe}">
    <option value="">-- Sélectionner --</option>
    <option th:each="sexe : ${sexes}" 
            th:value="${sexe.idSexe}" 
            th:text="${sexe.libelle}">
        Sexe option
    </option>
</select>

<!-- Afficher les erreurs de validation -->
<span th:if="${#fields.hasErrors('idSexe')}" 
      class="error-message" 
      th:errors="*{idSexe}">Erreur</span>
```

**Points clés à adapter:**
- `th:field="*{...}"` : binding bidirectionnel (objet Java ↔ HTML)
- `th:each` : parcourt les listes depuis le contrôleur
- `th:value`, `th:text` : attributs dynamiques
- `<form th:action="@{/demande}" method="POST" th:object="${demandeurDTO}">`

---

## Étape 2 : Créer un DTO (Data Transfer Object)

### Fichier: `src/main/java/com/itu/visamanagement/dto/DemandeurDTO.java`

**Objectif:** Mapper les données du formulaire (étapes 1-5) vers un objet Java

```java
package com.itu.visamanagement.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeurDTO {
    
    // ═══ ÉTAPE 1 : État civil ═══
    @NotBlank(message = "Le nom est requis")
    private String nom;
    
    @NotBlank(message = "Le prénom est requis")
    private String prenom;
    
    private String nomJeuneFille;
    
    @NotNull(message = "La date de naissance est requise")
    private LocalDate dateNaissance;
    
    private String lieuNaissance;
    
    @Email(message = "Format e-mail invalide")
    @NotBlank(message = "L'e-mail est requis")
    private String email;
    
    @NotBlank(message = "Le contact est requis")
    private String contact;
    
    @NotNull(message = "Le sexe est requis")
    private Integer idSexe;
    
    @NotNull(message = "La situation familiale est requise")
    private Integer idSituationFamiliale;
    
    @NotNull(message = "La nationalité est requise")
    private Integer idNationalite;
    
    @NotNull(message = "Le pays est requis")
    private Integer idPays;
    
    // ═══ ÉTAPE 2 : Passeport ═══
    @NotBlank(message = "Le numéro de passeport est requis")
    private String numeroPasseport;
    
    @NotNull(message = "La date de délivrance est requise")
    private LocalDate dateDelivrancePasseport;
    
    @NotNull(message = "La date d'expiration est requise")
    private LocalDate dateExpirationPasseport;
    
    // ═══ ÉTAPE 3 : Visa transformable ═══
    private String referenceVisa;
    private LocalDate dateEntreeMada;
    private String lieuEntree;
    private LocalDate dateExpirationVisa;
    
    // ═══ ÉTAPE 4 : Type de visa ═══
    @NotNull(message = "Le type de visa est requis")
    private Integer idTypeVisa;
    
    // ═══ ÉTAPE 5 : Type de demande ═══
    @NotNull(message = "Le type de demande est requis")
    private Integer idTypeDemandeVisa;
    
    // ═══ ÉTAPE 5 : Documents ═══
    private Integer[] documentsCommensIds;    // IDs des documents communs cochés
    private Integer[] documentsTypesIds;      // IDs des documents spécifiques cochés
}
```

**Note:** Utilise Jackson pour la sérialisation / validation Bean Validation

---

## Étape 3 : Créer/Adapter les Repositories

### Fichier: `src/main/java/com/itu/visamanagement/repository/DemandeurRepository.java`

```java
package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur, Integer> {
    Demandeur findByCode(String code);
    // Ajouter des custom queries si nécessaire
}
```

### Autres Repositories (lookup tables)

```java
// SexeRepository
public interface SexeRepository extends JpaRepository<Sexe, Integer> {}

// NationaliteRepository
public interface NationaliteRepository extends JpaRepository<Nationalite, Integer> {}

// PaysRepository
public interface PaysRepository extends JpaRepository<Pays, Integer> {}

// SituationFamilialeRepository
public interface SituationFamilialeRepository extends JpaRepository<SituationFamiliale, Integer> {}

// TypeVisaRepository
public interface TypeVisaRepository extends JpaRepository<TypeVisa, Integer> {}

// TypeDemandeVisaRepository
public interface TypeDemandeVisaRepository extends JpaRepository<TypeDemandeVisa, Integer> {}

// DocumentsTypesRepository
public interface DocumentsTypesRepository extends JpaRepository<DocumentsTypes, Integer> {}

// DocumentsCommunsRepository
public interface DocumentsCommunsRepository extends JpaRepository<DocumentsCommuns, Integer> {}
```

---

## Étape 4 : Créer le Service

### Fichier: `src/main/java/com/itu/visamanagement/services/DemandeurService.java`

```java
package com.itu.visamanagement.services;

import com.itu.visamanagement.dto.DemandeurDTO;
import com.itu.visamanagement.entity.*;
import com.itu.visamanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class DemandeurService {

    private final DemandeurRepository demandeurRepository;
    private final EtatCivilRepository etatCivilRepository;
    private final PasseportRepository passeportRepository;
    private final VisaTransformableRepository visaTransformableRepository;
    private final DemandeurDocumentsTypesRepository documentsTypesRepository;
    private final DemandeurDocumentsCommunsRepository documentsCommunsRepository;
    
    // Services lookup
    private final SexeRepository sexeRepository;
    private final NationaliteRepository nationaliteRepository;
    private final PaysRepository paysRepository;
    private final SituationFamilialeRepository situationFamilialeRepository;
    private final TypeVisaRepository typeVisaRepository;
    private final TypeDemandeVisaRepository typeDemandeVisaRepository;
    private final DocumentsTypesRepository documentsTypesLookupRepository;
    private final DocumentsCommunsRepository documentsCommunsLookupRepository;

    // Constructeur avec injection
    public DemandeurService(
        DemandeurRepository demandeurRepository,
        EtatCivilRepository etatCivilRepository,
        PasseportRepository passeportRepository,
        VisaTransformableRepository visaTransformableRepository,
        DemandeurDocumentsTypesRepository documentsTypesRepository,
        DemandeurDocumentsCommunsRepository documentsCommunsRepository,
        SexeRepository sexeRepository,
        NationaliteRepository nationaliteRepository,
        PaysRepository paysRepository,
        SituationFamilialeRepository situationFamilialeRepository,
        TypeVisaRepository typeVisaRepository,
        TypeDemandeVisaRepository typeDemandeVisaRepository,
        DocumentsTypesRepository documentsTypesLookupRepository,
        DocumentsCommunsRepository documentsCommunsLookupRepository
    ) {
        this.demandeurRepository = demandeurRepository;
        this.etatCivilRepository = etatCivilRepository;
        this.passeportRepository = passeportRepository;
        this.visaTransformableRepository = visaTransformableRepository;
        this.documentsTypesRepository = documentsTypesRepository;
        this.documentsCommunsRepository = documentsCommunsRepository;
        this.sexeRepository = sexeRepository;
        this.nationaliteRepository = nationaliteRepository;
        this.paysRepository = paysRepository;
        this.situationFamilialeRepository = situationFamilialeRepository;
        this.typeVisaRepository = typeVisaRepository;
        this.typeDemandeVisaRepository = typeDemandeVisaRepository;
        this.documentsTypesLookupRepository = documentsTypesLookupRepository;
        this.documentsCommunsLookupRepository = documentsCommunsLookupRepository;
    }

    /**
     * Crée une nouvelle demande à partir du DTO du formulaire
     */
    @Transactional
    public Demandeur createDemandeur(DemandeurDTO dto) {
        // Génère un code unique pour la demande
        String code = generateDemandeurCode();
        
        // Récupère les références (lookup)
        TypeVisa typeVisa = typeVisaRepository.findById(dto.getIdTypeVisa())
            .orElseThrow(() -> new RuntimeException("Type de visa non trouvé"));
        
        TypeDemandeVisa typeDemandeVisa = typeDemandeVisaRepository.findById(dto.getIdTypeDemandeVisa())
            .orElseThrow(() -> new RuntimeException("Type de demande non trouvé"));
        
        // Crée le Demandeur
        Demandeur demandeur = new Demandeur(code, typeVisa, typeDemandeVisa);
        demandeur = demandeurRepository.save(demandeur);
        
        // Crée l'EtatCivil associé
        EtatCivil etatCivil = new EtatCivil(
            dto.getNom(),
            dto.getPrenom(),
            situationFamilialeRepository.findById(dto.getIdSituationFamiliale()).orElseThrow(),
            sexeRepository.findById(dto.getIdSexe()).orElseThrow(),
            nationaliteRepository.findById(dto.getIdNationalite()).orElseThrow(),
            paysRepository.findById(dto.getIdPays()).orElseThrow(),
            demandeur
        );
        etatCivil.setNomJeuneFille(dto.getNomJeuneFille());
        etatCivil.setDateNaissance(dto.getDateNaissance());
        etatCivil.setLieuNaissance(dto.getLieuNaissance());
        etatCivil.setEmail(dto.getEmail());
        etatCivil.setContact(dto.getContact());
        etatCivil = etatCivilRepository.save(etatCivil);
        
        demandeur.setEtatCivil(etatCivil);
        
        // Crée le Passeport
        Passeport passeport = new Passeport(
            dto.getNumeroPasseport(),
            dto.getDateDelivrancePasseport(),
            dto.getDateExpirationPasseport(),
            etatCivil
        );
        passeport = passeportRepository.save(passeport);
        etatCivil.setPasseport(passeport);
        
        // Crée le VisaTransformable (optionnel)
        if (dto.getDateEntreeMada() != null) {
            VisaTransformable visaTransformable = new VisaTransformable(
                dto.getReferenceVisa(),
                dto.getDateEntreeMada(),
                dto.getLieuEntree(),
                dto.getDateExpirationVisa(),
                etatCivil
            );
            visaTransformable = visaTransformableRepository.save(visaTransformable);
            etatCivil.setVisaTransformable(visaTransformable);
        }
        
        // Associe les documents communs cochés
        if (dto.getDocumentsCommensIds() != null) {
            for (Integer docId : dto.getDocumentsCommensIds()) {
                DocumentsCommuns doc = documentsCommunsLookupRepository.findById(docId).orElseThrow();
                DemandeurDocumentsCommuns demandeurDocCommuns = new DemandeurDocumentsCommuns(true, demandeur, doc);
                documentsCommunsRepository.save(demandeurDocCommuns);
            }
        }
        
        // Associe les documents spécifiques cochés
        if (dto.getDocumentsTypesIds() != null) {
            for (Integer docId : dto.getDocumentsTypesIds()) {
                DocumentsTypes doc = documentsTypesLookupRepository.findById(docId).orElseThrow();
                DemandeurDocumentsTypes demandeurDocTypes = new DemandeurDocumentsTypes(true, doc, demandeur);
                documentsTypesRepository.save(demandeurDocTypes);
            }
        }
        
        return demandeurRepository.save(demandeur);
    }

    /**
     * Génère un code unique pour une demande
     */
    private String generateDemandeurCode() {
        // Format: D-YYYY-XXXXXX
        long count = demandeurRepository.count();
        int year = java.time.LocalDate.now().getYear();
        return String.format("D-%d-%06d", year, count + 1);
    }

    /**
     * Charge toutes les listes de lookup pour pré-remplir les selects
     */
    public Map<String, Object> getLookupData() {
        Map<String, Object> data = new HashMap<>();
        data.put("sexes", sexeRepository.findAll());
        data.put("nationalites", nationaliteRepository.findAll());
        data.put("pays", paysRepository.findAll());
        data.put("situationsFamiliales", situationFamilialeRepository.findAll());
        data.put("typesVisa", typeVisaRepository.findAll());
        data.put("typesDemandeVisa", typeDemandeVisaRepository.findAll());
        data.put("documentsCommuns", documentsCommunsLookupRepository.findAll());
        data.put("documentsTypes", documentsTypesLookupRepository.findAll());
        return data;
    }
}
```

---

## Étape 5 : Créer le Controller

### Fichier: `src/main/java/com/itu/visamanagement/controller/DemandeurController.java`

```java
package com.itu.visamanagement.controller;

import com.itu.visamanagement.dto.DemandeurDTO;
import com.itu.visamanagement.entity.Demandeur;
import com.itu.visamanagement.services.DemandeurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/demande")
public class DemandeurController {

    private final DemandeurService demandeurService;

    public DemandeurController(DemandeurService demandeurService) {
        this.demandeurService = demandeurService;
    }

    /**
     * GET /demande — Affiche le formulaire vide (étape 1)
     */
    @GetMapping
    public String showForm(Model model) {
        // Initialise un DTO vide
        model.addAttribute("demandeurDTO", new DemandeurDTO());
        
        // Charge les listes de lookup
        model.addAllAttributes(demandeurService.getLookupData());
        
        return "demande-form";  // Rend le template Thymeleaf
    }

    /**
     * POST /demande — Traite la soumission du formulaire
     */
    @PostMapping
    public String submitForm(
        @Valid @ModelAttribute("demandeurDTO") DemandeurDTO dto,
        BindingResult bindingResult,
        Model model
    ) {
        // Vérifie s'il y a des erreurs de validation
        if (bindingResult.hasErrors()) {
            // Recharge les lookups pour réafficher le formulaire
            model.addAllAttributes(demandeurService.getLookupData());
            return "demande-form";  // Retour au formulaire avec erreurs
        }

        try {
            // Crée la demande en base de données
            Demandeur demandeur = demandeurService.createDemandeur(dto);
            
            // Redirige vers un écran de succès avec le code de la demande
            return "redirect:/demande/success?code=" + demandeur.getCode();
        } catch (Exception e) {
            // En cas d'erreur, affiche un message
            model.addAttribute("error", "Erreur lors de la création de la demande: " + e.getMessage());
            model.addAllAttributes(demandeurService.getLookupData());
            return "demande-form";
        }
    }

    /**
     * GET /demande/success — Écran de confirmation
     */
    @GetMapping("/success")
    public String showSuccess(@RequestParam String code, Model model) {
        model.addAttribute("demandeurCode", code);
        return "demande-success";
    }
}
```

---

## Étape 6 : Créer les Repositories manquants

### Fichiers à créer

Crée les interfaces suivantes dans `src/main/java/com/itu/visamanagement/repository/`:

```java
// EtatCivilRepository.java
public interface EtatCivilRepository extends JpaRepository<EtatCivil, Integer> {}

// PasseportRepository.java
public interface PasseportRepository extends JpaRepository<Passeport, Integer> {}

// VisaTransformableRepository.java
public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Integer> {}

// DemandeurDocumentsTypesRepository.java
public interface DemandeurDocumentsTypesRepository extends JpaRepository<DemandeurDocumentsTypes, Integer> {}

// DemandeurDocumentsCommunsRepository.java
public interface DemandeurDocumentsCommuns extends JpaRepository<DemandeurDocumentsCommuns, Integer> {}
```

---

## Étape 7 : Adapter le template Thymeleaf

### Fichier: `src/main/resources/templates/demande-form.html`

**Points clés de transformation:**

```html
<!-- Début du formulaire -->
<form th:action="@{/demande}" method="POST" th:object="${demandeurDTO}">

<!-- Exemple d'adaptation pour un select -->
<select id="sexe" name="idSexe" th:field="*{idSexe}">
    <option value="">-- Sélectionner --</option>
    <option th:each="sexe : ${sexes}" 
            th:value="${sexe.idSexe}" 
            th:text="${sexe.libelle}">
    </option>
</select>

<!-- Affichage des erreurs -->
<span th:if="${#fields.hasErrors('idSexe')}" 
      class="error-message" 
      th:errors="*{idSexe}"></span>

<!-- Input de texte -->
<input type="text" id="nom" name="nom" th:field="*{nom}" placeholder="DUPONT">
<span th:if="${#fields.hasErrors('nom')}" 
      class="error-message" 
      th:errors="*{nom}"></span>

<!-- Fin du formulaire -->
</form>
```

---

## Étape 8 : Créer le template de succès

### Fichier: `src/main/resources/templates/demande-success.html`

```html
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Demande reçue</title>
</head>
<body>
    <div class="success-container">
        <h1>✓ Demande soumise avec succès</h1>
        <p>Votre code de référence: <strong th:text="${demandeurCode}"></strong></p>
        <p>Vous recevrez un e-mail de confirmation dans les prochaines heures.</p>
        <a href="/">Retour à l'accueil</a>
    </div>
</body>
</html>
```

---

## Étape 9 : Configuration Spring Boot

### Vérifier `application.properties`

```properties
# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Thymeleaf
spring.thymeleaf.enabled=true
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.check-template-location=true

# Validation
spring.mvc.locale=fr_FR
```

---

## Étape 10 : Support des messages d'erreur multilingues

### Fichier: `src/main/resources/messages.properties`

```properties
NotBlank.demandeurDTO.nom=Le nom est requis
NotBlank.demandeurDTO.prenom=Le prénom est requis
Email.demandeurDTO.email=L'adresse e-mail est invalide
NotNull.demandeurDTO.idSexe=Veuillez sélectionner le sexe
NotNull.demandeurDTO.idNationalite=Veuillez sélectionner la nationalité
```

---

## Résumé des fichiers à créer/modifier

| Fichier | Type | Rôle |
|---------|------|------|
| `DemandeurDTO.java` | Nouvelle | Mapping formulaire → objet Java |
| `DemandeurService.java` | Nouvelle | Logique métier, création demande |
| `DemandeurController.java` | Nouvelle | Gestion GET/POST, routage |
| `DemandeurRepository.java` | Nouvelle | Accès BD pour Demandeur |
| `EtatCivilRepository.java` | Nouvelle | Accès BD pour EtatCivil |
| `PasseportRepository.java` | Nouvelle | Accès BD pour Passeport |
| + autres repositories lookup | Nouvelle | Accès BD pour lookup tables |
| `demande-form.html` | Modifiée | Adaptation Thymeleaf (étapes 1-5) |
| `demande-success.html` | Nouvelle | Page de confirmation |
| `application.properties` | Vérifier | Config JPA, Thymeleaf |
| `messages.properties` | Nouvelle | Messages de validation FR |

---

## Flux complet de la demande

```
1. Utilisateur accède GET /demande
   └─→ Controller charge les lookups
   └─→ Template affiche étapes 1-5

2. Utilisateur remplit les 5 étapes
   └─→ JavaScript gère la navigation (pas de requête)

3. Utilisateur clique "Soumettre la demande"
   └─→ Formulaire POST /demande (toutes les données)

4. Controller valide avec @Valid
   ├─ Erreurs? → Retour au formulaire avec messages
   └─ OK? → Service crée les entités

5. Service crée:
   ├─ Demandeur
   ├─ EtatCivil
   ├─ Passeport
   ├─ VisaTransformable (optionnel)
   ├─ DemandeurDocumentsCommuns (liens)
   └─ DemandeurDocumentsTypes (liens)

6. Sauvegarde en PostgreSQL (transactions)

7. Redirection → GET /demande/success?code=D-2026-000001
   └─→ Affiche code de référence et confirmation
```

---

## Checklist d'implémentation

- [ ] Créer `DemandeurDTO.java`
- [ ] Créer tous les `*Repository.java`
- [ ] Créer `DemandeurService.java`
- [ ] Créer `DemandeurController.java`
- [ ] Adapter `demande-form.html` en Thymeleaf
- [ ] Créer `demande-success.html`
- [ ] Ajouter validation dans `messages.properties`
- [ ] Tester le formulaire complet (étapes 1-5)
- [ ] Vérifier persistance en BD
- [ ] Ajouter gestion des erreurs personnalisées
- [ ] Ajouter page de récapitulatif avant soumission (optionnel)

---

## Points d'attention particuliers

### 1. **Validation côté client vs serveur**
- JavaScript valide la navigation des étapes (UX)
- `@Valid` + Bean Validation valide côté serveur (sécurité)

### 2. **Gestion des transactions**
- `@Transactional` sur `createDemandeur()` pour garantir l'atomicité

### 3. **Checkbox dynamiques (Documents spécifiques)**
- Selon le type de visa sélectionné, les checkboxes changent
- Dans Thymeleaf, ajouter `th:if` conditionnel ou charger les docs via AJAX

### 4. **Redirection après soumission**
- Pattern POST-Redirect-GET pour éviter re-soumission accidentelle

### 5. **Sécurité CSRF**
- Thymeleaf ajoute automatiquement le token CSRF si Spring Security est activé
- Sinon, ajouter manuellement: `<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />`

---

**Généré le:** 16 avril 2026
