package com.sport.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sport.app.entity.Regle;

public interface RegleRepository extends JpaRepository<Regle, Long> {
    @Query("SELECT r FROM Regle r JOIN r.typesDeSport t WHERE t.id = :typeDeSportId")
    List<Regle> findByTypesDeSportId(@Param("typeDeSportId") Long typeDeSportId);

}