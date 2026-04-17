package com.itu.visamanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visa_transformable")
public class VisaTransformable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_visa_transformable")
    private Integer idVisaTransformable;

    @Column(name = "reference", length = 50)
    private String reference;

    @Column(name = "date_entree_mada")
    private LocalDate dateEntreeMada;

    @Column(name = "lieu", length = 50)
    private String lieu;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_etat_civil", nullable = false, unique = true)
    private EtatCivil etatCivil;

    // Constructors
    public VisaTransformable() {
    }

    public VisaTransformable(String reference, LocalDate dateEntreeMada, String lieu,
            LocalDate dateExpiration, EtatCivil etatCivil) {
        this.reference = reference;
        this.dateEntreeMada = dateEntreeMada;
        this.lieu = lieu;
        this.dateExpiration = dateExpiration;
        this.etatCivil = etatCivil;
    }

    // Getters and Setters
    public Integer getIdVisaTransformable() {
        return idVisaTransformable;
    }

    public void setIdVisaTransformable(Integer idVisaTransformable) {
        this.idVisaTransformable = idVisaTransformable;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateEntreeMada() {
        return dateEntreeMada;
    }

    public void setDateEntreeMada(LocalDate dateEntreeMada) {
        this.dateEntreeMada = dateEntreeMada;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }

    @Override
    public String toString() {
        return "VisaTransformable{" +
                "idVisaTransformable=" + idVisaTransformable +
                ", reference='" + reference + '\'' +
                ", dateEntreeMada=" + dateEntreeMada +
                ", lieu='" + lieu + '\'' +
                ", dateExpiration=" + dateExpiration +
                '}';
    }
}
