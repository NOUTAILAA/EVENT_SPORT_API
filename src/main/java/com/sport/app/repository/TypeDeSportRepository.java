package com.sport.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.TypeDeSport;

@Repository
public interface TypeDeSportRepository extends JpaRepository<TypeDeSport, Long> {
}
