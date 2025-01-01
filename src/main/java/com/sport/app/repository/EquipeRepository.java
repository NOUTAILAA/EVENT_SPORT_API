package com.sport.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
	 // Custom query to find teams by participant ID
    @Query("SELECT e FROM Equipe e JOIN e.participants p WHERE p.id = :participantId")
    List<Equipe> findByParticipantsId(@Param("participantId") Long participantId);

}
