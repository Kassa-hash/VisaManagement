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
    private final DemandeurDocumentsTypesRepository documentsTypesRepository;
    private final DemandeurDocumentsCommunsRepository documentsCommunsRepository;

    private final SexeRepository sexeRepository;
    private final NationaliteRepository nationaliteRepository;
    private final PaysRepository paysRepository;
    private final SituationFamilialeRepository situationFamilialeRepository;
    private final TypeVisaRepository typeVisaRepository;
    private final TypeDemandeVisaRepository typeDemandeVisaRepository;
    private final DocumentsTypesRepository documentsTypesLookupRepository;
    private final DocumentsCommunsRepository documentsCommunsLookupRepository;

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
            DocumentsCommunsRepository documentsCommunsLookupRepository) {
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
                demandeur);
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
                etatCivil);
        passeport = passeportRepository.save(passeport);
        etatCivil.setPasseport(passeport);

        // Crée le VisaTransformable (optionnel)
        if (dto.getDateEntreeMada() != null) {
            VisaTransformable visaTransformable = new VisaTransformable(
                    dto.getReferenceVisa(),
                    dto.getDateEntreeMada(),
                    dto.getLieuEntree(),
                    dto.getDateExpirationVisa(),
                    etatCivil);
            visaTransformable = visaTransformableRepository.save(visaTransformable);
            etatCivil.setVisaTransformable(visaTransformable);
        }

        // Associe les documents communs cochés
        if (dto.getDocumentsCommensIds() != null && dto.getDocumentsCommensIds().length > 0) {
            for (Integer docId : dto.getDocumentsCommensIds()) {
                DocumentsCommuns doc = documentsCommunsLookupRepository.findById(docId).orElseThrow();
                DemandeurDocumentsCommuns demandeurDocCommuns = new DemandeurDocumentsCommuns(true, demandeur, doc);
                documentsCommunsRepository.save(demandeurDocCommuns);
            }
        }

        // Associe les documents spécifiques cochés
        if (dto.getDocumentsTypesIds() != null && dto.getDocumentsTypesIds().length > 0) {
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
        long count = demandeurRepository.count();
        int year = LocalDate.now().getYear();
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
