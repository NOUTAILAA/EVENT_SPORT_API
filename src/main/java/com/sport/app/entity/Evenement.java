package com.sport.app.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")

public class Evenement {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date date;
    private Double prix;

    @ManyToOne
    private Organisateur organisateur;

    @ManyToOne
    private TypeDeSport typeDeSport;
    @ManyToOne
    @JoinColumn(name = "localisation_id")
    private Localisation localisation;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Equipe> equipes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "evenement_participant",
        joinColumns = @JoinColumn(name = "evenement_id"),
        inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participant> participants = new ArrayList<>();
   
    
    
    
    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL)
    private List<Resultat> resultats = new ArrayList<>();

    
    
    @ManyToMany
    @JoinTable(
        name = "evenement_promotion",
        joinColumns = @JoinColumn(name = "evenement_id"),
        inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    private List<Promotion> promotions = new ArrayList<>();
    // Autres champs et méthodes...

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }


    
    
    public boolean ajouterParticipant(Participant participant) {
        // Vérifier si le nombre maximum d'équipes a été atteint
        if (equipes.size() >= typeDeSport.getNombreEquipesMax()) {
            // Si le nombre d'équipes maximum est atteint, vérifier si elles ont de la place
            for (Equipe equipe : equipes) {
                if (equipe.getParticipants().size() < typeDeSport.getNombreParticipantsParEquipe()) {
                    equipe.ajouterParticipant(participant);
                    return true;
                }
            }
            // Si toutes les équipes sont pleines
            return false;
        } else {
            // Créer une nouvelle équipe si nécessaire et l'associer à l'événement
            Equipe nouvelleEquipe = new Equipe();
            nouvelleEquipe.setEvenement(this);
            nouvelleEquipe.ajouterParticipant(participant);
            equipes.add(nouvelleEquipe);
            return true;
        }
    }



    public void repartirParticipantsAleatoirement(List<Participant> participants) {
        Collections.shuffle(participants);  // Mélange aléatoire des participants
        int index = 0;
        for (Participant participant : participants) {
            Equipe equipe = equipes.get(index % equipes.size());
            equipe.ajouterParticipant(participant);
            index++;
        }
    }

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Organisateur getOrganisateur() {
		return organisateur;
	}

	public void setOrganisateur(Organisateur organisateur) {
		this.organisateur = organisateur;
	}

	public TypeDeSport getTypeDeSport() {
		return typeDeSport;
	}

	public void setTypeDeSport(TypeDeSport typeDeSport) {
		this.typeDeSport = typeDeSport;
	}

	public List<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}
	public Localisation getLocalisation() {
        return localisation;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }
	public Evenement(Long id, String nom, Date date, Organisateur organisateur, TypeDeSport typeDeSport,
            Localisation localisation, List<Equipe> equipes) {
this.id = id;
this.nom = nom;
this.date = date;
this.organisateur = organisateur;
this.typeDeSport = typeDeSport;
this.localisation = localisation;
this.equipes = equipes;
}
	public Evenement(Long id, String nom, Date date, Organisateur organisateur, TypeDeSport typeDeSport,
			List<Equipe> equipes) {
		super();
		this.id = id;
		this.nom = nom;
		this.date = date;
		this.organisateur = organisateur;
		this.typeDeSport = typeDeSport;
		this.equipes = equipes;
	}
	
public Evenement(Long id, String nom, Date date, Double prix, Organisateur organisateur, TypeDeSport typeDeSport,
			Localisation localisation, List<Equipe> equipes, List<Participant> participants, List<Resultat> resultats,
			List<Promotion> promotions) {
		super();
		this.id = id;
		this.nom = nom;
		this.date = date;
		this.prix = prix;
		this.organisateur = organisateur;
		this.typeDeSport = typeDeSport;
		this.localisation = localisation;
		this.equipes = equipes;
		this.participants = participants;
		this.resultats = resultats;
		this.promotions = promotions;
	}

public Evenement(Long id, String nom, Date date, Double prix, Organisateur organisateur, TypeDeSport typeDeSport,
			Localisation localisation, List<Equipe> equipes, List<Participant> participants, List<Resultat> resultats) {
		super();
		this.id = id;
		this.nom = nom;
		this.date = date;
		this.prix = prix;
		this.organisateur = organisateur;
		this.typeDeSport = typeDeSport;
		this.localisation = localisation;
		this.equipes = equipes;
		this.participants = participants;
		this.resultats = resultats;
	}

public Evenement() {}
    // Getters and Setters
public List<Resultat> getResultats() {
    return resultats;
}

public void setResultats(List<Resultat> resultats) {
    this.resultats = resultats;
}
public Double getPrix() {
    return prix;
}

public void setPrix(Double prix) {
    this.prix = prix;
}





}
