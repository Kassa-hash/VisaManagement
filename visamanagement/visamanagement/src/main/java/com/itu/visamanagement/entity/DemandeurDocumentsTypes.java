package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demandeur_documents_types")
public class DemandeurDocumentsTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_demandeur_documents")
    private Integer idDemandeurDocuments;

    @Column(name = "is_ok")
    private Boolean isOk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_documents_types", nullable = false)
    private DocumentsTypes documentsTypes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_demandeur", nullable = false)
    private Demandeur demandeur;

    // Constructors
    public DemandeurDocumentsTypes() {
    }

    public DemandeurDocumentsTypes(Boolean isOk, DocumentsTypes documentsTypes, Demandeur demandeur) {
        this.isOk = isOk;
        this.documentsTypes = documentsTypes;
        this.demandeur = demandeur;
    }

    // Getters and Setters
    public Integer getIdDemandeurDocuments() {
        return idDemandeurDocuments;
    }

    public void setIdDemandeurDocuments(Integer idDemandeurDocuments) {
        this.idDemandeurDocuments = idDemandeurDocuments;
    }

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

    public DocumentsTypes getDocumentsTypes() {
        return documentsTypes;
    }

    public void setDocumentsTypes(DocumentsTypes documentsTypes) {
        this.documentsTypes = documentsTypes;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    @Override
    public String toString() {
        return "DemandeurDocumentsTypes{" +
                "idDemandeurDocuments=" + idDemandeurDocuments +
                ", isOk=" + isOk +
                '}';
    }
}
