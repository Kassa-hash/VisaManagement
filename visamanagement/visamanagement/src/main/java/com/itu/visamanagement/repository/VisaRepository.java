package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Integer> {
}
