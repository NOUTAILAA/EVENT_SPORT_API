package com.sport.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.Evenement;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {

    List<Evenement> findByTypeDeSportId(Long typeDeSportId);
    @Query("SELECT e FROM Evenement e JOIN e.participants p WHERE p.id = :participantId")
    List<Evenement> findByParticipantsId(@Param("participantId") Long participantId);
}
