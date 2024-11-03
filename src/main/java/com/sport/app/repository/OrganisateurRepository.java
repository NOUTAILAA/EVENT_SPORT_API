package com.sport.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.Organisateur;

@Repository
public interface OrganisateurRepository extends JpaRepository<Organisateur, Long> {
}
