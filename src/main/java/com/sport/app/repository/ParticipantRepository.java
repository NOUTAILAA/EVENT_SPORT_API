package com.sport.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.Participant;
import com.sport.app.entity.User;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	 @Query("SELECT p.id AS id, p.name AS name, p.email AS email, p.telephone AS telephone, p.password AS password FROM Participant p")
	    List<Object[]> findAllSimpleParticipants();
	    Participant findByEmailAndPassword(String email, String password);
	    Optional<Participant> findByPassword(String password);
	    User findByEmail(String email);

}
