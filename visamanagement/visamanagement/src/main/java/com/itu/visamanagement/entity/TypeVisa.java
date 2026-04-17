package com.itu.visamanagement.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "type_visa")
public class TypeVisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_type_visa")
    private Integer idTypeVisa;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @OneToMany(mappedBy = "typeVisa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Demandeur> demandeurs;

    // Constructors
    public TypeVisa() {
    }

    public TypeVisa(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters
    public Integer getIdTypeVisa() {
        return idTypeVisa;
    }

    public void setIdTypeVisa(Integer idTypeVisa) {
        this.idTypeVisa = idTypeVisa;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Demandeur> getDemandeurs() {
        return demandeurs;
    }

    public void setDemandeurs(List<Demandeur> demandeurs) {
        this.demandeurs = demandeurs;
    }

    @Override
    public String toString() {
        return "TypeVisa{" +
                "idTypeVisa=" + idTypeVisa +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
