package com.sport.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	 @Query("SELECT p.id AS id, p.name AS name, p.email AS email, p.telephone AS telephone, p.password AS password FROM Participant p")
	    List<Object[]> findAllSimpleParticipants();
}
