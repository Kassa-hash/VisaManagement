package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Integer> {
}
