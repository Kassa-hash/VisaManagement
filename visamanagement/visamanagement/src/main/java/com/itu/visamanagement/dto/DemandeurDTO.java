package com.itu.visamanagement.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public class DemandeurDTO {

    // ═══ ÉTAPE 1 : État civil ═══
    @NotBlank(message = "Le nom est requis")
    private String nom;

    @NotBlank(message = "Le prénom est requis")
    private String prenom;

    private String nomJeuneFille;

    @NotNull(message = "La date de naissance est requise")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;

    private String lieuNaissance;

    @Email(message = "Format e-mail invalide")
    @NotBlank(message = "L'e-mail est requis")
    private String email;

    @NotBlank(message = "Le contact est requis")
    private String contact;

    private String adresseMada;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDelivrancePasseport;

    @NotNull(message = "La date d'expiration est requise")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateExpirationPasseport;

    // ═══ ÉTAPE 3 : Visa transformable ═══
    private String referenceVisa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEntreeMada;
    private String lieuEntree;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateExpirationVisa;

    // ═══ ÉTAPE 4 : Type de visa ═══
    @NotNull(message = "Le type de visa est requis")
    private Integer idTypeVisa;

    // ═══ ÉTAPE 5 : Type de demande ═══
    @NotNull(message = "Le type de demande est requis")
    private Integer idTypeDemandeVisa;

    // ═══ ÉTAPE 5 : Documents ═══
    private Integer[] documentsCommensIds;
    private Integer[] documentsTypesIds;

    // Constructors
    public DemandeurDTO() {
    }

    // Getters and Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomJeuneFille() {
        return nomJeuneFille;
    }

    public void setNomJeuneFille(String nomJeuneFille) {
        this.nomJeuneFille = nomJeuneFille;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresseMada() {
        return adresseMada;
    }

    public void setAdresseMada(String adresseMada) {
        this.adresseMada = adresseMada;
    }

    public Integer getIdSexe() {
        return idSexe;
    }

    public void setIdSexe(Integer idSexe) {
        this.idSexe = idSexe;
    }

    public Integer getIdSituationFamiliale() {
        return idSituationFamiliale;
    }

    public void setIdSituationFamiliale(Integer idSituationFamiliale) {
        this.idSituationFamiliale = idSituationFamiliale;
    }

    public Integer getIdNationalite() {
        return idNationalite;
    }

    public void setIdNationalite(Integer idNationalite) {
        this.idNationalite = idNationalite;
    }

    public Integer getIdPays() {
        return idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    public String getNumeroPasseport() {
        return numeroPasseport;
    }

    public void setNumeroPasseport(String numeroPasseport) {
        this.numeroPasseport = numeroPasseport;
    }

    public LocalDate getDateDelivrancePasseport() {
        return dateDelivrancePasseport;
    }

    public void setDateDelivrancePasseport(LocalDate dateDelivrancePasseport) {
        this.dateDelivrancePasseport = dateDelivrancePasseport;
    }

    public LocalDate getDateExpirationPasseport() {
        return dateExpirationPasseport;
    }

    public void setDateExpirationPasseport(LocalDate dateExpirationPasseport) {
        this.dateExpirationPasseport = dateExpirationPasseport;
    }

    public String getReferenceVisa() {
        return referenceVisa;
    }

    public void setReferenceVisa(String referenceVisa) {
        this.referenceVisa = referenceVisa;
    }

    public LocalDate getDateEntreeMada() {
        return dateEntreeMada;
    }

    public void setDateEntreeMada(LocalDate dateEntreeMada) {
        this.dateEntreeMada = dateEntreeMada;
    }

    public String getLieuEntree() {
        return lieuEntree;
    }

    public void setLieuEntree(String lieuEntree) {
        this.lieuEntree = lieuEntree;
    }

    public LocalDate getDateExpirationVisa() {
        return dateExpirationVisa;
    }

    public void setDateExpirationVisa(LocalDate dateExpirationVisa) {
        this.dateExpirationVisa = dateExpirationVisa;
    }

    public Integer getIdTypeVisa() {
        return idTypeVisa;
    }

    public void setIdTypeVisa(Integer idTypeVisa) {
        this.idTypeVisa = idTypeVisa;
    }

    public Integer getIdTypeDemandeVisa() {
        return idTypeDemandeVisa;
    }

    public void setIdTypeDemandeVisa(Integer idTypeDemandeVisa) {
        this.idTypeDemandeVisa = idTypeDemandeVisa;
    }

    public Integer[] getDocumentsCommensIds() {
        return documentsCommensIds;
    }

    public void setDocumentsCommensIds(Integer[] documentsCommensIds) {
        this.documentsCommensIds = documentsCommensIds;
    }

    public Integer[] getDocumentsTypesIds() {
        return documentsTypesIds;
    }

    public void setDocumentsTypesIds(Integer[] documentsTypesIds) {
        this.documentsTypesIds = documentsTypesIds;
    }
}
