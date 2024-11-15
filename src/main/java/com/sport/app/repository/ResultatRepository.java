package com.sport.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sport.app.entity.Resultat;

public interface ResultatRepository extends JpaRepository <Resultat, Long >{
    @Query("SELECT r FROM Resultat r WHERE r.evenement.id = :evenementId ORDER BY r.temps ASC")
    List<Resultat> findResultatsByEvenementIdSortedByTemps(Long evenementId);
    @Query("SELECT r FROM Resultat r WHERE r.evenement.id = :evenementId ORDER BY r.nombreButs DESC")
    List<Resultat> findByEvenementIdSortedByButs(Long evenementId);

    @Query("SELECT r FROM Resultat r WHERE r.evenement.typeDeSport.id = :typeSportId")
    List<Resultat> findByTypeDeSportId(Long typeSportId);

}
