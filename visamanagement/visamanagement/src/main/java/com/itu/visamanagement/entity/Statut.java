package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "statut")
public class Statut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_statut")
    private Integer idStatut;

    @Column(name = "libelle", length = 50)
    private String libelle;

    // Constructors
    public Statut() {
    }

    public Statut(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters
    public Integer getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(Integer idStatut) {
        this.idStatut = idStatut;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
