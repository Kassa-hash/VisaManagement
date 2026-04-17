package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demandeur_documents_communs")
public class DemandeurDocumentsCommuns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_demandeur_documents_communs")
    private Integer idDemandeurDocumentsCommuns;

    @Column(name = "is_ok")
    private Boolean isOk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_demandeur", nullable = false)
    private Demandeur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_documents_commune", nullable = false)
    private DocumentsCommuns documentsCommuns;

    // Constructors
    public DemandeurDocumentsCommuns() {
    }

    public DemandeurDocumentsCommuns(Boolean isOk, Demandeur demandeur, DocumentsCommuns documentsCommuns) {
        this.isOk = isOk;
        this.demandeur = demandeur;
        this.documentsCommuns = documentsCommuns;
    }

    // Getters and Setters
    public Integer getIdDemandeurDocumentsCommuns() {
        return idDemandeurDocumentsCommuns;
    }

    public void setIdDemandeurDocumentsCommuns(Integer idDemandeurDocumentsCommuns) {
        this.idDemandeurDocumentsCommuns = idDemandeurDocumentsCommuns;
    }

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public DocumentsCommuns getDocumentsCommuns() {
        return documentsCommuns;
    }

    public void setDocumentsCommuns(DocumentsCommuns documentsCommuns) {
        this.documentsCommuns = documentsCommuns;
    }

    @Override
    public String toString() {
        return "DemandeurDocumentsCommuns{" +
                "idDemandeurDocumentsCommuns=" + idDemandeurDocumentsCommuns +
                ", isOk=" + isOk +
                '}';
    }
}
