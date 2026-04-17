package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.DocumentsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsTypesRepository extends JpaRepository<DocumentsTypes, Integer> {
}
