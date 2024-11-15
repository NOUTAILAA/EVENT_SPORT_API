
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
    property = "id"
)
public class Resultat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nombreButs; // Nombre de buts pour les sports comme le football

    private Double temps; // Temps réalisé pour les sports comme le marathon, nullable

    @ManyToOne
    private Equipe equipe; // L'équipe concernée par le résultat

    @ManyToOne
    private Participant participant; // Le participant concerné par le résultat

    @ManyToOne
    private Evenement evenement; // L'événement associé au résultat

    // Getters and setters
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

    public Double getTemps() {
        return temps;
    }

    public void setTemps(Double temps) {
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

    public Resultat(int nombreButs, Double temps, Equipe equipe, Participant participant, Evenement evenement) {
        this.nombreButs = nombreButs;
        this.temps = temps;
        this.equipe = equipe;
        this.participant = participant;
        this.evenement = evenement;
    }
    

    public Resultat(int nombreButs, double temps, Equipe equipe, Participant participant, Evenement evenement) {
        this.nombreButs = nombreButs;
        this.temps = temps;
        this.equipe = equipe;
        this.participant = participant;
        this.evenement = evenement;
    }
}

