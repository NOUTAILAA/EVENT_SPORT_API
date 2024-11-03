package com.sport.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
}
