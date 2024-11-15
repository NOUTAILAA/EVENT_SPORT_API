package com.sport.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sport.app.entity.Classement;
import com.sport.app.entity.Evenement;
import com.sport.app.entity.Resultat;

public interface ClassementRepository extends JpaRepository<Classement, Long> {
    List<Classement> findAllByEvenement(Evenement evenement);
    @Query("SELECT c FROM Classement c WHERE c.evenement.id = :evenementId ORDER BY c.score DESC")
    List<Classement> findClassementsByEvenementIdSorted(Long evenementId);
}
