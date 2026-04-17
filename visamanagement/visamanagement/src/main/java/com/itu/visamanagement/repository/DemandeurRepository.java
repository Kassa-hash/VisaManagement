package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur, Integer> {
    Demandeur findByCode(String code);
}
