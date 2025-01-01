package com.sport.app.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class TypeDeSport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int nombreEquipesMax;
    private int nombreParticipantsParEquipe;
    @ManyToMany
    @JoinTable(
            name = "type_sport_regle",
            joinColumns = @JoinColumn(name = "type_sport_id"),
            inverseJoinColumns = @JoinColumn(name = "regle_id")
    )
    private Set<Regle> regles = new HashSet<>();  // Initialize as a HashSet to avoid null



    public boolean estValidePourEvenement(int nombreEquipes, int nombreParticipants) {
        return nombreEquipes <= nombreEquipesMax && nombreParticipants <= nombreParticipantsParEquipe;
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

	public int getNombreEquipesMax() {
		return nombreEquipesMax;
	}

	public void setNombreEquipesMax(int nombreEquipesMax) {
		this.nombreEquipesMax = nombreEquipesMax;
	}

	public int getNombreParticipantsParEquipe() {
		return nombreParticipantsParEquipe;
	}

	public void setNombreParticipantsParEquipe(int nombreParticipantsParEquipe) {
		this.nombreParticipantsParEquipe = nombreParticipantsParEquipe;
	}

	public TypeDeSport(Long id, String nom, int nombreEquipesMax, int nombreParticipantsParEquipe) {
		super();
		this.id = id;
		this.nom = nom;
		this.nombreEquipesMax = nombreEquipesMax;
		this.nombreParticipantsParEquipe = nombreParticipantsParEquipe;
	}
    public TypeDeSport() {}

	public Set<Regle> getRegles() {
		return regles;
	}

	public void setRegles(Set<Regle> regles) {
		this.regles = regles;
	}


}