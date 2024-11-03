package com.sport.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.Evenement;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {
}
