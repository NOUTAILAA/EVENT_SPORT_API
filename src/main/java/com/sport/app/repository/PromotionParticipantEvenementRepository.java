package com.sport.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sport.app.entity.PromotionParticipantEvenement;

public interface PromotionParticipantEvenementRepository extends JpaRepository<PromotionParticipantEvenement, Long> {
    List<PromotionParticipantEvenement> findByParticipantId(Long participantId);
    Optional<PromotionParticipantEvenement> findByEvenementIdAndParticipantId(Long evenementId, Long participantId);

}
