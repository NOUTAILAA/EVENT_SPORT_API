package com.sport.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sport.app.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findByCode(String code);
    @Query("SELECT p FROM Promotion p JOIN p.participants par WHERE par.id = :participantId")
    List<Promotion> findByParticipantsId(@Param("participantId") Long participantId);


}
