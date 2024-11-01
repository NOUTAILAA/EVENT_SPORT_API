package com.sport.app.entites;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String date;
    private String heure;
    @ManyToOne
    @JoinColumn(name = "type_sport_id")
    private TypeSport typeSport; // Type de sport associé à l'événement

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location; // chaque événement a une seule location.

    @ManyToOne
    @JoinColumn(name = "organisateur_id")
    private Organisateur organisateur; // un événement est associé à un seul organisateur.
    @ManyToMany
    @JoinTable(
        name = "evenement_equipe",
        joinColumns = @JoinColumn(name = "evenement_id"),
        inverseJoinColumns = @JoinColumn(name = "equipe_id")
    )
    private List<Equipe> equipes; // Une liste d'équipes participant à l'événement

    @ManyToMany(mappedBy = "evenements")
    private List<Participant> participants; //un participant peut être associé à plusieurs événements, et chaque événement peut avoir plusieurs participants.
    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL)
    private List<MiseAJour> misesAJour; // Un Evenement peut avoir plusieurs MiseAJour.
    public Evenement(String nom, String date, String heure, TypeSport typeSport) {
        this.nom = nom;
        this.date = date;
        this.heure = heure;
        this.typeSport = typeSport;
    }
    public Evenement(Long id, String nom, String date, String heure, TypeSport typeSport, Location location,
            Organisateur organisateur, List<Participant> participants, List<Equipe> equipes, List<MiseAJour> misesAJour) {
this.id = id;
this.nom = nom;
this.date = date;
this.heure = heure;
this.typeSport = typeSport;
this.location = location;
this.organisateur = organisateur;
this.participants = participants;
this.equipes = equipes;
this.misesAJour = misesAJour;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHeure() {
		return heure;
	}
	public void setHeure(String heure) {
		this.heure = heure;
	}
	public TypeSport getTypeSport() {
		return typeSport;
	}
	public void setTypeSport(TypeSport typeSport) {
		this.typeSport = typeSport;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Organisateur getOrganisateur() {
		return organisateur;
	}
	public void setOrganisateur(Organisateur organisateur) {
		this.organisateur = organisateur;
	}
	public List<Participant> getParticipants() {
		return participants;
	}
	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
	public List<MiseAJour> getMisesAJour() {
		return misesAJour;
	}
	public void setMisesAJour(List<MiseAJour> misesAJour) {
		this.misesAJour = misesAJour;
	}
    
	public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }
public Evenement() {}
}
