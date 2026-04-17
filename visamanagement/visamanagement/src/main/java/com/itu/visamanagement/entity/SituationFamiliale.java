package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "situation_familiale")
public class SituationFamiliale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_situation_familiale")
    private Integer idSituationFamiliale;

    @Column(name = "libelle", length = 50)
    private String libelle;

    // Constructors
    public SituationFamiliale() {
    }

    public SituationFamiliale(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters
    public Integer getIdSituationFamiliale() {
        return idSituationFamiliale;
    }

    public void setIdSituationFamiliale(Integer idSituationFamiliale) {
        this.idSituationFamiliale = idSituationFamiliale;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "SituationFamiliale{" +
                "idSituationFamiliale=" + idSituationFamiliale +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
