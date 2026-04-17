package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sexe")
public class Sexe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_sexe")
    private Integer idSexe;

    @Column(name = "libelle", length = 50)
    private String libelle;

    // Constructors
    public Sexe() {
    }

    public Sexe(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters
    public Integer getIdSexe() {
        return idSexe;
    }

    public void setIdSexe(Integer idSexe) {
        this.idSexe = idSexe;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Sexe{" +
                "idSexe=" + idSexe +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
