package com.sport.app.repository;

import com.sport.app.entity.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalisationRepository extends JpaRepository<Localisation, Long> {
    // You can add custom query methods if needed, but JpaRepository already provides basic CRUD operations.
}
