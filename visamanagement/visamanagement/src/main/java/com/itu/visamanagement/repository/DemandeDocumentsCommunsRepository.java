package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.DemandeDocumentsCommuns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeDocumentsCommunsRepository extends JpaRepository<DemandeDocumentsCommuns, Integer> {
}
