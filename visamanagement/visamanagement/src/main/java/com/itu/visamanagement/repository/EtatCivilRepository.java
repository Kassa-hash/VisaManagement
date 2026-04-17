package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.EtatCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatCivilRepository extends JpaRepository<EtatCivil, Integer> {
}
