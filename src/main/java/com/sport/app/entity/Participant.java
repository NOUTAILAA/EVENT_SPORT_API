package com.sport.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")

public class Participant extends User {
	@ManyToMany(mappedBy = "participants")
    private List<Evenement> evenementsParticipes = new ArrayList<>();
	 
	
	
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
	    private List<Resultat> resultats = new ArrayList<>();

    @ManyToMany(mappedBy = "participants")
    private List<Equipe> equipes = new ArrayList<>(); // Nouvelle relation

    @ManyToMany(mappedBy = "participants")
    private List<Promotion> promotions = new ArrayList<>();

	
	public Participant(Long id, String name, String email, String password, Long telephone,
			List<Evenement> evenementsParticipes, List<Resultat> resultats, List<Equipe> equipes,
			List<Promotion> promotions) {
		super(id, name, email, password, telephone);
		this.evenementsParticipes = evenementsParticipes;
		this.resultats = resultats;
		this.equipes = equipes;
		this.promotions = promotions;
	}

	public List<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

	public List<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}

	public List<Evenement> getEvenementsParticipes() {
		return evenementsParticipes;
	}

	public void setEvenementsParticipes(List<Evenement> evenementsParticipes) {
		this.evenementsParticipes = evenementsParticipes;
	}

	public Participant(Long id, String name, String email, String password, Long telephone,
			List<Evenement> evenementsParticipes) {
		super(id, name, email, password, telephone);
		this.evenementsParticipes = evenementsParticipes;
	}
	
public Participant(Long id, String name, String email, String password, Long telephone) {
		super(id, name, email, password, telephone);
	}



public Participant(Long id, String name, String email, String password, Long telephone, List<Resultat> resultats,
		List<Equipe> equipes, List<Promotion> promotions) {
	super(id, name, email, password, telephone);
	this.resultats = resultats;
	this.equipes = equipes;
	this.promotions = promotions;
}

public List<Resultat> getResultats() {
    return resultats;
}

public void setResultats(List<Resultat> resultats) {
    this.resultats = resultats;
}

public Participant(Long id, String name, String email, String password, Long telephone,
		List<Evenement> evenementsParticipes, List<Resultat> resultats) {
	super(id, name, email, password, telephone);
	this.evenementsParticipes = evenementsParticipes;
	this.resultats = resultats;
}
public Participant() {}
}