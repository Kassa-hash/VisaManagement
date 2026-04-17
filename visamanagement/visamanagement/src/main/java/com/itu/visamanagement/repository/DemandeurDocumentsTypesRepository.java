package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.DemandeurDocumentsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeurDocumentsTypesRepository extends JpaRepository<DemandeurDocumentsTypes, Integer> {
}
