package com.itu.visamanagement.config;

import com.itu.visamanagement.entity.*;
import com.itu.visamanagement.repository.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public ApplicationRunner initializeData(
            SexeRepository sexeRepository,
            NationaliteRepository nationaliteRepository,
            PaysRepository paysRepository,
            SituationFamilialeRepository situationFamilialeRepository,
            TypeVisaRepository typeVisaRepository,
            TypeDemandeVisaRepository typeDemandeVisaRepository,
            DocumentsCommunsRepository documentsCommunsRepository,
            DocumentsTypesRepository documentsTypesRepository,
            StatutRepository statutRepository) {

        return args -> {
            // Insert Sexe
            if (sexeRepository.count() == 0) {
                sexeRepository.saveAll(Arrays.asList(
                        new Sexe("Masculin"),
                        new Sexe("Féminin")));
            }

            // Insert Nationalite
            if (nationaliteRepository.count() == 0) {
                nationaliteRepository.saveAll(Arrays.asList(
                        new Nationalite("Malagasy"),
                        new Nationalite("Français"),
                        new Nationalite("Américain"),
                        new Nationalite("Britannique"),
                        new Nationalite("Allemand"),
                        new Nationalite("Chinois"),
                        new Nationalite("Indien")));
            }

            // Insert Pays
            if (paysRepository.count() == 0) {
                paysRepository.saveAll(Arrays.asList(
                        new Pays("Madagascar"),
                        new Pays("France"),
                        new Pays("États-Unis"),
                        new Pays("Royaume-Uni"),
                        new Pays("Allemagne"),
                        new Pays("Chine"),
                        new Pays("Inde"),
                        new Pays("Japon"),
                        new Pays("Afrique du Sud")));
            }

            // Insert SituationFamiliale
            if (situationFamilialeRepository.count() == 0) {
                situationFamilialeRepository.saveAll(Arrays.asList(
                        new SituationFamiliale("Célibataire"),
                        new SituationFamiliale("Marié(e)"),
                        new SituationFamiliale("Divorcé(e)"),
                        new SituationFamiliale("Veuf/Veuve"),
                        new SituationFamiliale("Pacsé(e)")));
            }

            // Insert TypeVisa
            if (typeVisaRepository.count() == 0) {
                typeVisaRepository.saveAll(Arrays.asList(
                        new TypeVisa("Visa Investisseur"),
                        new TypeVisa("Visa Travailleur"),
                        new TypeVisa("Visa Étudiant"),
                        new TypeVisa("Visa Touristique"),
                        new TypeVisa("Visa Familial")));
            }

            // Insert TypeDemandeVisa
            if (typeDemandeVisaRepository.count() == 0) {
                typeDemandeVisaRepository.saveAll(Arrays.asList(
                        new TypeDemandeVisa("Première demande"),
                        new TypeDemandeVisa("Renouvellement"),
                        new TypeDemandeVisa("Transformation")));
            }

            // Insert DocumentsCommuns
            if (documentsCommunsRepository.count() == 0) {
                documentsCommunsRepository.saveAll(Arrays.asList(
                        new DocumentsCommuns("Extrait de naissance"),
                        new DocumentsCommuns("Certificat de mariage"),
                        new DocumentsCommuns("Certificat de divorce"),
                        new DocumentsCommuns("Passeport"),
                        new DocumentsCommuns("Carte d'identité"),
                        new DocumentsCommuns("Certificat de résidence"),
                        new DocumentsCommuns("Casier judiciaire")));
            }

            // Insert DocumentsTypes
            if (documentsTypesRepository.count() == 0) {
                documentsTypesRepository.saveAll(Arrays.asList(
                        new DocumentsTypes("Certificat de scolarité"),
                        new DocumentsTypes("Lettre d'acceptation universitaire"),
                        new DocumentsTypes("Contrat de travail"),
                        new DocumentsTypes("Preuve de résidence"),
                        new DocumentsTypes("Justificatif de revenus"),
                        new DocumentsTypes("Assurance maladie"),
                        new DocumentsTypes("Bilan de santé"),
                        new DocumentsTypes("Attestation d'hébergement"),
                        new DocumentsTypes("Certificat d'investissement")));
            }

            // Insert Statut
            if (statutRepository.count() == 0) {
                statutRepository.saveAll(Arrays.asList(
                        new Statut("En attente"),
                        new Statut("Acceptée"),
                        new Statut("Refusée"),
                        new Statut("Suspendue"),
                        new Statut("En cours de traitement")));
            }
        };
    }
}
