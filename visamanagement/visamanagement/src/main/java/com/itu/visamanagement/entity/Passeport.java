package com.itu.visamanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passeport")
public class Passeport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_passeport")
    private Integer idPasseport;

    @Column(name = "numero", length = 50)
    private String numero;

    @Column(name = "date_delivrance")
    private LocalDate dateDelivrance;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_etat_civil", nullable = false, unique = true)
    private EtatCivil etatCivil;

    // Constructors
    public Passeport() {
    }

    public Passeport(String numero, LocalDate dateDelivrance, LocalDate dateExpiration, EtatCivil etatCivil) {
        this.numero = numero;
        this.dateDelivrance = dateDelivrance;
        this.dateExpiration = dateExpiration;
        this.etatCivil = etatCivil;
    }

    // Getters and Setters
    public Integer getIdPasseport() {
        return idPasseport;
    }

    public void setIdPasseport(Integer idPasseport) {
        this.idPasseport = idPasseport;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateDelivrance(LocalDate dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
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
        return "Passeport{" +
                "idPasseport=" + idPasseport +
                ", numero='" + numero + '\'' +
                ", dateDelivrance=" + dateDelivrance +
                ", dateExpiration=" + dateExpiration +
                '}';
    }
}
