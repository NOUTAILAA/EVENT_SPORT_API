package com.sport.app.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Classement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Evenement evenement;

    @ManyToOne
    private Equipe equipe;

    @ManyToOne
    private Participant participant;

    private double score; // Peut être le temps ou le nombre de buts selon le sport

    // Constructors, getters and setters
    public Classement() {}

    public Classement(Evenement evenement, Equipe equipe, Participant participant, double score) {
        this.evenement = evenement;
        this.equipe = equipe;
        this.participant = participant;
        this.score = score;
    }

    // Standard getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Evenement getEvenement() { return evenement; }
    public void setEvenement(Evenement evenement) { this.evenement = evenement; }
    public Equipe getEquipe() { return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }
    public Participant getParticipant() { return participant; }
    public void setParticipant(Participant participant) { this.participant = participant; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
}
