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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_passeport", nullable = false)
    private Passeport passeport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_demandeur", nullable = false)
    private Demandeur demandeur;

    // Constructors
    public VisaTransformable() {
    }

    public VisaTransformable(String reference, LocalDate dateEntreeMada, String lieu,
            LocalDate dateExpiration, Passeport passeport, Demandeur demandeur) {
        this.reference = reference;
        this.dateEntreeMada = dateEntreeMada;
        this.lieu = lieu;
        this.dateExpiration = dateExpiration;
        this.passeport = passeport;
        this.demandeur = demandeur;
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

    public Passeport getPasseport() {
        return passeport;
    }

    public void setPasseport(Passeport passeport) {
        this.passeport = passeport;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
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
