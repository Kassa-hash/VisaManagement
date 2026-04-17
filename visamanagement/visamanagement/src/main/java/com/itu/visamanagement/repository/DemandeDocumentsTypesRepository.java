package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.DemandeDocumentsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeDocumentsTypesRepository extends JpaRepository<DemandeDocumentsTypes, Integer> {
}
