package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutRepository extends JpaRepository<Statut, Integer> {
}
