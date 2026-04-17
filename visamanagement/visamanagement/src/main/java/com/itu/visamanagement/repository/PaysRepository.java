package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysRepository extends JpaRepository<Pays, Integer> {
}
