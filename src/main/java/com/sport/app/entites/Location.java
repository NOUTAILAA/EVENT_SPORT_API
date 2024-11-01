package com.sport.app.entites;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Evenement> evenements; //une location peut accueillir plusieurs événements

    // Getters, Setters, Constructors, etc.
}
