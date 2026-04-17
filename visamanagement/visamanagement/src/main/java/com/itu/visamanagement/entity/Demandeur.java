package com.itu.visamanagement.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "demandeur")
public class Demandeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_demandeur")
    private Integer idDemandeur;

    @Column(name = "code", length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_type_visa", nullable = false)
    private TypeVisa typeVisa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_type_demande_visa", nullable = false)
    private TypeDemandeVisa typeDemandeVisa;

    @OneToOne(mappedBy = "demandeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private EtatCivil etatCivil;

    @OneToMany(mappedBy = "demandeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandeurDocumentsTypes> documentsTypes;

    @OneToMany(mappedBy = "demandeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandeurDocumentsCommuns> documentsCommuns;

    // Constructors
    public Demandeur() {
    }

    public Demandeur(String code, TypeVisa typeVisa, TypeDemandeVisa typeDemandeVisa) {
        this.code = code;
        this.typeVisa = typeVisa;
        this.typeDemandeVisa = typeDemandeVisa;
    }

    // Getters and Setters
    public Integer getIdDemandeur() {
        return idDemandeur;
    }

    public void setIdDemandeur(Integer idDemandeur) {
        this.idDemandeur = idDemandeur;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TypeVisa getTypeVisa() {
        return typeVisa;
    }

    public void setTypeVisa(TypeVisa typeVisa) {
        this.typeVisa = typeVisa;
    }

    public TypeDemandeVisa getTypeDemandeVisa() {
        return typeDemandeVisa;
    }

    public void setTypeDemandeVisa(TypeDemandeVisa typeDemandeVisa) {
        this.typeDemandeVisa = typeDemandeVisa;
    }

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }

    public List<DemandeurDocumentsTypes> getDocumentsTypes() {
        return documentsTypes;
    }

    public void setDocumentsTypes(List<DemandeurDocumentsTypes> documentsTypes) {
        this.documentsTypes = documentsTypes;
    }

    public List<DemandeurDocumentsCommuns> getDocumentsCommuns() {
        return documentsCommuns;
    }

    public void setDocumentsCommuns(List<DemandeurDocumentsCommuns> documentsCommuns) {
        this.documentsCommuns = documentsCommuns;
    }

    @Override
    public String toString() {
        return "Demandeur{" +
                "idDemandeur=" + idDemandeur +
                ", code='" + code + '\'' +
                '}';
    }
}
