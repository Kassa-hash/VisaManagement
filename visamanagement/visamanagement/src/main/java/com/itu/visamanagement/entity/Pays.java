package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Pays")
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Pays")
    private Integer idPays;

    @Column(name = "libelle", length = 50)
    private String libelle;

    // Constructors
    public Pays() {
    }

    public Pays(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters
    public Integer getIdPays() {
        return idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Pays{" +
                "idPays=" + idPays +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
