package com.itu.visamanagement.repository;

import com.itu.visamanagement.entity.VisaTransformable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Integer> {
}
