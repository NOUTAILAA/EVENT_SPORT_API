package com.sport.app.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Organisateur extends User {
	 @OneToMany(mappedBy = "organisateur", cascade = CascadeType.ALL)
	    private List<Evenement> evenementsOrganises = new ArrayList<>();

	public List<Evenement> getEvenementsOrganises() {
		return evenementsOrganises;
	}

	public void setEvenementsOrganises(List<Evenement> evenementsOrganises) {
		this.evenementsOrganises = evenementsOrganises;
	}

	public Organisateur(Long id, String name, String email, String password, Long telephone,
			List<Evenement> evenementsOrganises) {
		super(id, name, email, password, telephone);
		this.evenementsOrganises = evenementsOrganises;
	}
	public Organisateur(){}
	
}