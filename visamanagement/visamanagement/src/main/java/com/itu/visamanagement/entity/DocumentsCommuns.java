package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "documents_communs")
public class DocumentsCommuns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_documents_commune")
    private Integer idDocumentsCommune;

    @Column(name = "libelle", length = 50)
    private String libelle;

    // Constructors
    public DocumentsCommuns() {
    }

    public DocumentsCommuns(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters
    public Integer getIdDocumentsCommune() {
        return idDocumentsCommune;
    }

    public void setIdDocumentsCommune(Integer idDocumentsCommune) {
        this.idDocumentsCommune = idDocumentsCommune;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "DocumentsCommuns{" +
                "idDocumentsCommune=" + idDocumentsCommune +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
