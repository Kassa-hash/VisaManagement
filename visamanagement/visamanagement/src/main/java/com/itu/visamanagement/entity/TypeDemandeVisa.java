package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_demande_visa")
public class TypeDemandeVisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_type_demande_visa")
    private Integer idTypeDemandeVisa;

    @Column(name = "libelle", length = 50)
    private String libelle;

    // Constructors
    public TypeDemandeVisa() {
    }

    public TypeDemandeVisa(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters
    public Integer getIdTypeDemandeVisa() {
        return idTypeDemandeVisa;
    }

    public void setIdTypeDemandeVisa(Integer idTypeDemandeVisa) {
        this.idTypeDemandeVisa = idTypeDemandeVisa;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "TypeDemandeVisa{" +
                "idTypeDemandeVisa=" + idTypeDemandeVisa +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
