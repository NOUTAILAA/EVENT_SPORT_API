package com.sport.app.entites;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;

@Entity
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "equipe")
    private List<Classement> classements; // Liste des classements associés à l'équipe

    @OneToMany(mappedBy = "equipe")
    private List<Participant> participants;

    @ManyToMany
    private List<Evenement> evenements; // Les événements auxquels l'équipe participe

public Equipe() {}
    public Equipe(String name) {
        this.name = name;
    }

    public Equipe(Long id, String name, List<Classement> classements, List<Participant> participants, List<Evenement> evenements) {
        this.id = id;
        this.name = name;
        this.classements = classements;
        this.participants = participants;
        this.evenements = evenements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Classement> getClassements() {
        return classements;
    }

    public void setClassements(List<Classement> classements) {
        this.classements = classements;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
}
