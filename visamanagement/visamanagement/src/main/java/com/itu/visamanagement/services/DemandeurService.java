package com.itu.visamanagement.services;

import com.itu.visamanagement.dto.DemandeurDTO;
import com.itu.visamanagement.entity.*;
import com.itu.visamanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class DemandeurService {

    private final DemandeurRepository demandeurRepository;
    private final EtatCivilRepository etatCivilRepository;
    private final PasseportRepository passeportRepository;
    private final VisaTransformableRepository visaTransformableRepository;
    private final DemandeRepository demandeRepository;
    private final DemandeDocumentsTypesRepository demandeDocumentsTypesRepository;
    private final DemandeDocumentsCommunsRepository demandeDocumentsCommunsRepository;

    private final SexeRepository sexeRepository;
    private final NationaliteRepository nationaliteRepository;
    private final PaysRepository paysRepository;
    private final SituationFamilialeRepository situationFamilialeRepository;
    private final TypeVisaRepository typeVisaRepository;
    private final TypeDemandeVisaRepository typeDemandeVisaRepository;
    private final StatutRepository statutRepository;
    private final DocumentsTypesRepository documentsTypesRepository;
    private final DocumentsCommunsRepository documentsCommunsRepository;

    public DemandeurService(
            DemandeurRepository demandeurRepository,
            EtatCivilRepository etatCivilRepository,
            PasseportRepository passeportRepository,
            VisaTransformableRepository visaTransformableRepository,
            DemandeRepository demandeRepository,
            DemandeDocumentsTypesRepository demandeDocumentsTypesRepository,
            DemandeDocumentsCommunsRepository demandeDocumentsCommunsRepository,
            SexeRepository sexeRepository,
            NationaliteRepository nationaliteRepository,
            PaysRepository paysRepository,
            SituationFamilialeRepository situationFamilialeRepository,
            TypeVisaRepository typeVisaRepository,
            TypeDemandeVisaRepository typeDemandeVisaRepository,
            StatutRepository statutRepository,
            DocumentsTypesRepository documentsTypesRepository,
            DocumentsCommunsRepository documentsCommunsRepository) {
        this.demandeurRepository = demandeurRepository;
        this.etatCivilRepository = etatCivilRepository;
        this.passeportRepository = passeportRepository;
        this.visaTransformableRepository = visaTransformableRepository;
        this.demandeRepository = demandeRepository;
        this.demandeDocumentsTypesRepository = demandeDocumentsTypesRepository;
        this.demandeDocumentsCommunsRepository = demandeDocumentsCommunsRepository;
        this.sexeRepository = sexeRepository;
        this.nationaliteRepository = nationaliteRepository;
        this.paysRepository = paysRepository;
        this.situationFamilialeRepository = situationFamilialeRepository;
        this.typeVisaRepository = typeVisaRepository;
        this.typeDemandeVisaRepository = typeDemandeVisaRepository;
        this.statutRepository = statutRepository;
        this.documentsTypesRepository = documentsTypesRepository;
        this.documentsCommunsRepository = documentsCommunsRepository;
    }

    /**
     * Crée une nouvelle demande complète à partir du DTO du formulaire
     */
    @Transactional
    public String createDemande(DemandeurDTO dto) {
        // Génère un code unique pour le demandeur
        String code = generateDemandeurCode();

        // Crée le Demandeur
        Demandeur demandeur = new Demandeur(code);
        demandeur = demandeurRepository.save(demandeur);

        // Crée l'EtatCivil associé
        EtatCivil etatCivil = new EtatCivil(
                dto.getNom(),
                dto.getPrenom(),
                situationFamilialeRepository.findById(dto.getIdSituationFamiliale()).orElseThrow(),
                sexeRepository.findById(dto.getIdSexe()).orElseThrow(),
                nationaliteRepository.findById(dto.getIdNationalite()).orElseThrow(),
                paysRepository.findById(dto.getIdPays()).orElseThrow(),
                demandeur);
        etatCivil.setNomJeuneFille(dto.getNomJeuneFille());
        etatCivil.setDateNaissance(dto.getDateNaissance());
        etatCivil.setLieuNaissance(dto.getLieuNaissance());
        etatCivil.setEmail(dto.getEmail());
        etatCivil.setContact(dto.getContact());
        etatCivil.setAdresseMada(dto.getAdresseMada());
        etatCivil = etatCivilRepository.save(etatCivil);

        // Crée le Passeport
        Passeport passeport = new Passeport(
                dto.getNumeroPasseport(),
                dto.getDateDelivrancePasseport(),
                dto.getDateExpirationPasseport(),
                demandeur);
        passeport = passeportRepository.save(passeport);

        // Crée le VisaTransformable (optionnel)
        if (dto.getDateEntreeMada() != null) {
            VisaTransformable visaTransformable = new VisaTransformable(
                    dto.getReferenceVisa(),
                    dto.getDateEntreeMada(),
                    dto.getLieuEntree(),
                    dto.getDateExpirationVisa(),
                    passeport,
                    demandeur);
            visaTransformableRepository.save(visaTransformable);
        }

        // Récupère les entités de référence pour la demande
        TypeVisa typeVisa = typeVisaRepository.findById(dto.getIdTypeVisa())
                .orElseThrow(() -> new RuntimeException("Type de visa non trouvé"));

        TypeDemandeVisa typeDemandeVisa = typeDemandeVisaRepository.findById(dto.getIdTypeDemandeVisa())
                .orElseThrow(() -> new RuntimeException("Type de demande non trouvé"));

        // Récupère le statut initial (optionnel, peut être null ou défini par défaut)
        Statut statut = statutRepository.findById(1) // Statut par défaut: "En attente"
                .orElseThrow(() -> new RuntimeException("Statut par défaut non trouvé"));

        // Crée la Demande
        Demande demande = new Demande(
                LocalDate.now(),
                typeVisa,
                demandeur,
                statut,
                typeDemandeVisa);
        demande = demandeRepository.save(demande);

        // Associe les documents communs cochés
        if (dto.getDocumentsCommensIds() != null && dto.getDocumentsCommensIds().length > 0) {
            for (Integer docId : dto.getDocumentsCommensIds()) {
                DocumentsCommuns doc = documentsCommunsRepository.findById(docId).orElseThrow();
                DemandeDocumentsCommuns demandeDocCommuns = new DemandeDocumentsCommuns(demande, doc);
                demandeDocumentsCommunsRepository.save(demandeDocCommuns);
            }
        }

        // Associe les documents spécifiques cochés
        if (dto.getDocumentsTypesIds() != null && dto.getDocumentsTypesIds().length > 0) {
            for (Integer docId : dto.getDocumentsTypesIds()) {
                DocumentsTypes doc = documentsTypesRepository.findById(docId).orElseThrow();
                DemandeDocumentsTypes demandeDocTypes = new DemandeDocumentsTypes(demande, doc);
                demandeDocumentsTypesRepository.save(demandeDocTypes);
            }
        }

        return code;
    }

    /**
     * Génère un code unique pour un demandeur
     */
    private String generateDemandeurCode() {
        long count = demandeurRepository.count();
        int year = LocalDate.now().getYear();
        return String.format("D-%d-%06d", year, count + 1);
    }

    /**
     * Charge toutes les listes de lookup pour pré-remplir les dropdowns
     */
    public Map<String, Object> getLookupData() {
        Map<String, Object> data = new HashMap<>();
        data.put("sexes", sexeRepository.findAll());
        data.put("nationalites", nationaliteRepository.findAll());
        data.put("pays", paysRepository.findAll());
        data.put("situationsFamiliales", situationFamilialeRepository.findAll());
        data.put("typesVisa", typeVisaRepository.findAll());
        data.put("typesDemandeVisa", typeDemandeVisaRepository.findAll());
        data.put("documentsCommuns", documentsCommunsRepository.findAll());
        data.put("documentsTypes", documentsTypesRepository.findAll());
        return data;
    }
}
