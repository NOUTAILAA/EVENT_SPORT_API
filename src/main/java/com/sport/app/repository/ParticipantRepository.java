package com.sport.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sport.app.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
