package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.Sexe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexeRepository extends JpaRepository<Sexe, Integer> {
}
