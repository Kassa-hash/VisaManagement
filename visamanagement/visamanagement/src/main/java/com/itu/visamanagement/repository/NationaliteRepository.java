package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.Nationalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationaliteRepository extends JpaRepository<Nationalite, Integer> {
}
