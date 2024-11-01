package com.sport.app.entites;


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
    private TypeSport typeSport; // Type de sport pour lequel le classement est établi

    @ManyToOne
    private Equipe equipe; // Équipe concernée par le classement

    @ManyToOne
    private Participant participant; // Participant concerné par le classement

    private Integer rank; // Rang dans le classement

	public Classement(Long id, TypeSport typeSport, Equipe equipe, Participant participant, Integer rank) {
		super();
		this.id = id;
		this.typeSport = typeSport;
		this.equipe = equipe;
		this.participant = participant;
		this.rank = rank;
	}

    public Classement() {}
}
