package com.sport.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")

public class Participant extends User {
	@ManyToMany(mappedBy = "participants")
    private List<Evenement> evenementsParticipes = new ArrayList<>();

	public List<Evenement> getEvenementsParticipes() {
		return evenementsParticipes;
	}

	public void setEvenementsParticipes(List<Evenement> evenementsParticipes) {
		this.evenementsParticipes = evenementsParticipes;
	}

	public Participant(Long id, String name, String email, String password, int telephone,
			List<Evenement> evenementsParticipes) {
		super(id, name, email, password, telephone);
		this.evenementsParticipes = evenementsParticipes;
	}
public Participant() {}
}