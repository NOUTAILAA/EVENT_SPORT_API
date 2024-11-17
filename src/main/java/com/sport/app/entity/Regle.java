package com.sport.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Regle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "regles")
    @JsonIgnore
    private List<TypeDeSport> typesDeSport;

    public Regle() {}

    public Regle(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TypeDeSport> getTypesDeSport() {
        return typesDeSport;
    }

    public void setTypesDeSport(List<TypeDeSport> typesDeSport) {
        this.typesDeSport = typesDeSport;
    }
}