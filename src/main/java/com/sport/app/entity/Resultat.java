package com.sport.app.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")

public class Resultat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nombreButs; // Nombre de buts (ou temps pour le marathon)

    private double temps; // Temps réalisé pour les sports comme le marathon (heures, minutes, secondes)

    @ManyToOne
    private Equipe equipe; // Cas pour les sports d'équipe

    @ManyToOne
    private Participant participant; // Cas pour les sports individuels

    @ManyToOne
    private Evenement evenement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNombreButs() {
        return nombreButs;
    }

    public void setNombreButs(int nombreButs) {
        this.nombreButs = nombreButs;
    }

    public double getTemps() {
        return temps;
    }

    public void setTemps(double temps) {
        this.temps = temps;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
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

    public Resultat() {}

    public Resultat(int nombreButs, double temps, Equipe equipe, Participant participant, Evenement evenement) {
        this.nombreButs = nombreButs;
        this.temps = temps;
        this.equipe = equipe;
        this.participant = participant;
        this.evenement = evenement;
    }
}
