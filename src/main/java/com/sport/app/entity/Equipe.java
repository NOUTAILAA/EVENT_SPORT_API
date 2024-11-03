package com.sport.app.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToOne
    private Evenement evenement;

    @OneToMany
    private List<Participant> participants = new ArrayList<>();

    public void ajouterParticipant(Participant participant) {
        if (participants.size() < this.getEvenement().getTypeDeSport().getNombreParticipantsParEquipe()) {
            participants.add(participant);
        }else {
            throw new RuntimeException("Impossible d'ajouter le participant. Soit l'événement est null, soit l'équipe est pleine.");
        }
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Evenement getEvenement() {
		return evenement;
	}

	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public Equipe(Long id, String nom, Evenement evenement, List<Participant> participants) {
		super();
		this.id = id;
		this.nom = nom;
		this.evenement = evenement;
		this.participants = participants;
	}
public Equipe() {}
}
