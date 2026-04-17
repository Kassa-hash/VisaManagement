package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "documents_types")
public class DocumentsTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_documents_types")
    private Integer idDocumentsTypes;

    @Column(name = "libelle", length = 50)
    private String libelle;

    // Constructors
    public DocumentsTypes() {
    }

    public DocumentsTypes(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters
    public Integer getIdDocumentsTypes() {
        return idDocumentsTypes;
    }

    public void setIdDocumentsTypes(Integer idDocumentsTypes) {
        this.idDocumentsTypes = idDocumentsTypes;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "DocumentsTypes{" +
                "idDocumentsTypes=" + idDocumentsTypes +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
