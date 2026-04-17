package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.Passeport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasseportRepository extends JpaRepository<Passeport, Integer> {
}
