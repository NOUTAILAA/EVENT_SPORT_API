package com.sport.app.entites;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MiseAJour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evenement_id")
    private Evenement evenement; // Chaque MiseAJour est liée à un unique Evenement

    private String contenu; // Contenu de la mise à jour

    // Getters, Setters, Constructors, etc.
}
