package com.sport.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PromotionParticipantEvenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "evenement_id")
    private Evenement evenement;

    private Double prixApresRemise;

    public PromotionParticipantEvenement() {}

    public PromotionParticipantEvenement(Promotion promotion, Participant participant, Evenement evenement, Double prixApresRemise) {
        this.promotion = promotion;
        this.participant = participant;
        this.evenement = evenement;
        this.prixApresRemise = prixApresRemise;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Double getPrixApresRemise() {
        return prixApresRemise;
    }

    public void setPrixApresRemise(Double prixApresRemise) {
        this.prixApresRemise = prixApresRemise;
    }
    
}
