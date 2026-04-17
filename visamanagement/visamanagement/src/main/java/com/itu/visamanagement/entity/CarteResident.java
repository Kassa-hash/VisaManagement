package com.itu.visamanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "carte_resident")
public class CarteResident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_carte_resident")
    private Integer idCarteResident;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_passeport", nullable = false)
    private Passeport passeport;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_demande", nullable = false, unique = true)
    private Demande demande;

    // Constructors
    public CarteResident() {
    }

    public CarteResident(LocalDate dateDebut, LocalDate dateFin, Passeport passeport, Demande demande) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.passeport = passeport;
        this.demande = demande;
    }

    // Getters and Setters
    public Integer getIdCarteResident() {
        return idCarteResident;
    }

    public void setIdCarteResident(Integer idCarteResident) {
        this.idCarteResident = idCarteResident;
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

    public Passeport getPasseport() {
        return passeport;
    }

    public void setPasseport(Passeport passeport) {
        this.passeport = passeport;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }
}
