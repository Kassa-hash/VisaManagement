package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demande_documents_communs")
public class DemandeDocumentsCommuns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_demande_documents_communs")
    private Integer idDemandeDocumentsCommuns;

    @Column(name = "is_ok")
    private Boolean isOk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_demande", nullable = false)
    private Demande demande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_documents_communs", nullable = false)
    private DocumentsCommuns documentsCommuns;

    // Constructors
    public DemandeDocumentsCommuns() {
    }

    public DemandeDocumentsCommuns(Demande demande, DocumentsCommuns documentsCommuns) {
        this.demande = demande;
        this.documentsCommuns = documentsCommuns;
        this.isOk = false;
    }

    // Getters and Setters
    public Integer getIdDemandeDocumentsCommuns() {
        return idDemandeDocumentsCommuns;
    }

    public void setIdDemandeDocumentsCommuns(Integer idDemandeDocumentsCommuns) {
        this.idDemandeDocumentsCommuns = idDemandeDocumentsCommuns;
    }

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public DocumentsCommuns getDocumentsCommuns() {
        return documentsCommuns;
    }

    public void setDocumentsCommuns(DocumentsCommuns documentsCommuns) {
        this.documentsCommuns = documentsCommuns;
    }
}
