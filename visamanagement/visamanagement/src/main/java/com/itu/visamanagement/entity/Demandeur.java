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

    @OneToOne(mappedBy = "demandeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private EtatCivil etatCivil;

    @OneToMany(mappedBy = "demandeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passeport> passeports;

    @OneToMany(mappedBy = "demandeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisaTransformable> visasTransformables;

    @OneToMany(mappedBy = "demandeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Demande> demandes;

    // Constructors
    public Demandeur() {
    }

    public Demandeur(String code) {
        this.code = code;
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

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }

    public List<Passeport> getPasseports() {
        return passeports;
    }

    public void setPasseports(List<Passeport> passeports) {
        this.passeports = passeports;
    }

    public List<VisaTransformable> getVisasTransformables() {
        return visasTransformables;
    }

    public void setVisasTransformables(List<VisaTransformable> visasTransformables) {
        this.visasTransformables = visasTransformables;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    @Override
    public String toString() {
        return "Demandeur{" +
                "idDemandeur=" + idDemandeur +
                ", code='" + code + '\'' +
                '}';
    }
}
