package com.itu.visamanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "demande")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_demande")
    private Integer idDemande;

    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_type_visa", nullable = false)
    private TypeVisa typeVisa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_demandeur", nullable = false)
    private Demandeur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_statut", nullable = false)
    private Statut statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_type_demande_visa", nullable = false)
    private TypeDemandeVisa typeDemandeVisa;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandeDocumentsTypes> demandeDocumentsTypes;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandeDocumentsCommuns> demandeDocumentsCommuns;

    // Constructors
    public Demande() {
    }

    public Demande(LocalDate dateDemande, TypeVisa typeVisa, Demandeur demandeur,
            Statut statut, TypeDemandeVisa typeDemandeVisa) {
        this.dateDemande = dateDemande;
        this.typeVisa = typeVisa;
        this.demandeur = demandeur;
        this.statut = statut;
        this.typeDemandeVisa = typeDemandeVisa;
    }

    // Getters and Setters
    public Integer getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Integer idDemande) {
        this.idDemande = idDemande;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public TypeVisa getTypeVisa() {
        return typeVisa;
    }

    public void setTypeVisa(TypeVisa typeVisa) {
        this.typeVisa = typeVisa;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public TypeDemandeVisa getTypeDemandeVisa() {
        return typeDemandeVisa;
    }

    public void setTypeDemandeVisa(TypeDemandeVisa typeDemandeVisa) {
        this.typeDemandeVisa = typeDemandeVisa;
    }

    public List<DemandeDocumentsTypes> getDemandeDocumentsTypes() {
        return demandeDocumentsTypes;
    }

    public void setDemandeDocumentsTypes(List<DemandeDocumentsTypes> demandeDocumentsTypes) {
        this.demandeDocumentsTypes = demandeDocumentsTypes;
    }

    public List<DemandeDocumentsCommuns> getDemandeDocumentsCommuns() {
        return demandeDocumentsCommuns;
    }

    public void setDemandeDocumentsCommuns(List<DemandeDocumentsCommuns> demandeDocumentsCommuns) {
        this.demandeDocumentsCommuns = demandeDocumentsCommuns;
    }
}
