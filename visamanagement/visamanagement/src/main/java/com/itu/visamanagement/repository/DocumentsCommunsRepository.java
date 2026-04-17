package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.DocumentsCommuns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsCommunsRepository extends JpaRepository<DocumentsCommuns, Integer> {
}
