package com.itu.visamanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visa")
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_visa")
    private Integer idVisa;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_demande", nullable = false, unique = true)
    private Demande demande;

    // Constructors
    public Visa() {
    }

    public Visa(LocalDate dateDebut, LocalDate dateFin, Demande demande) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.demande = demande;
    }

    // Getters and Setters
    public Integer getIdVisa() {
        return idVisa;
    }

    public void setIdVisa(Integer idVisa) {
        this.idVisa = idVisa;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }
}
