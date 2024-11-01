package com.sport.app.entites;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class TypeSport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double prix; // Prix associé à ce type de sport

    @OneToMany(mappedBy = "typeSport", cascade = CascadeType.ALL)
    private List<Regle> regles = new ArrayList<>();

    // Getters, Setters, Constructors, etc.
}
