package com.itu.visamanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demande_documents_types")
public class DemandeDocumentsTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_demande_documents")
    private Integer idDemandeDocuments;

    @Column(name = "is_ok")
    private Boolean isOk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_demande", nullable = false)
    private Demande demande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_documents_types", nullable = false)
    private DocumentsTypes documentsTypes;

    // Constructors
    public DemandeDocumentsTypes() {
    }

    public DemandeDocumentsTypes(Demande demande, DocumentsTypes documentsTypes) {
        this.demande = demande;
        this.documentsTypes = documentsTypes;
        this.isOk = false;
    }

    // Getters and Setters
    public Integer getIdDemandeDocuments() {
        return idDemandeDocuments;
    }

    public void setIdDemandeDocuments(Integer idDemandeDocuments) {
        this.idDemandeDocuments = idDemandeDocuments;
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

    public DocumentsTypes getDocumentsTypes() {
        return documentsTypes;
    }

    public void setDocumentsTypes(DocumentsTypes documentsTypes) {
        this.documentsTypes = documentsTypes;
    }
}
