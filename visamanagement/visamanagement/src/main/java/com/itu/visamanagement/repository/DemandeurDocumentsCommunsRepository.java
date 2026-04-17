package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.DemandeurDocumentsCommuns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeurDocumentsCommunsRepository extends JpaRepository<DemandeurDocumentsCommuns, Integer> {
}
