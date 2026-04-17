package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nationalite")
public class Nationalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_nationalite")
    private Integer idNationalite;

    @Column(name = "libelle", length = 50)
    private String libelle;

    // Constructors
    public Nationalite() {
    }

    public Nationalite(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters
    public Integer getIdNationalite() {
        return idNationalite;
    }

    public void setIdNationalite(Integer idNationalite) {
        this.idNationalite = idNationalite;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Nationalite{" +
                "idNationalite=" + idNationalite +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
