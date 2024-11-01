package com.sport.app.entites;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Organisateur extends User {
    @OneToMany(mappedBy = "organisateur", cascade = CascadeType.ALL)
    private List<Evenement> evenements; // un organisateur peut creer plusieurs événements.

    // Getters, Setters, Constructors, etc.
}
