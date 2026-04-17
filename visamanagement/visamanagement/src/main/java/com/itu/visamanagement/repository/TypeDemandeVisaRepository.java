package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.TypeDemandeVisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDemandeVisaRepository extends JpaRepository<TypeDemandeVisa, Integer> {
}
