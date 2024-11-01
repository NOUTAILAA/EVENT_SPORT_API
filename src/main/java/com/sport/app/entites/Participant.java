package com.sport.app.entites;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Participant extends User {
    @ManyToMany
    @JoinTable(
        name = "evenement_participant",
        joinColumns = @JoinColumn(name = "participant_id"),
        inverseJoinColumns = @JoinColumn(name = "evenement_id")
    )
    private List<Evenement> evenements; // un participant peut être associé à plusieurs événements, et chaque événement peut avoir plusieurs participants.

    // Getters, Setters, Constructors, etc.
}
