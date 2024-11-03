package com.sport.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Equipe;
import com.sport.app.service.EquipeService;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @PostMapping("/creer/{evenementId}")
    public ResponseEntity<Equipe> creerEquipe(@PathVariable Long evenementId, @RequestBody Equipe equipe) {
        Equipe nouvelleEquipe = equipeService.creerEquipe(evenementId, equipe);
        return new ResponseEntity<>(nouvelleEquipe, HttpStatus.CREATED);
    }

    @GetMapping("/{equipeId}")
    public ResponseEntity<Equipe> obtenirEquipe(@PathVariable Long equipeId) {
        Equipe equipe = equipeService.obtenirEquipe(equipeId);
        return new ResponseEntity<>(equipe, HttpStatus.OK);
    }

    @GetMapping("/evenement/{evenementId}")
    public ResponseEntity<List<Equipe>> obtenirEquipesParEvenement(@PathVariable Long evenementId) {
        List<Equipe> equipes = equipeService.obtenirEquipesParEvenement(evenementId);
        return new ResponseEntity<>(equipes, HttpStatus.OK);
    }

    @PostMapping("/{equipeId}/ajouter-participant/{participantId}")
    public ResponseEntity<String> ajouterParticipantDansEquipe(@PathVariable Long equipeId, @PathVariable Long participantId) {
        equipeService.ajouterParticipantDansEquipe(equipeId, participantId);
        return new ResponseEntity<>("Participant ajouté à l'équipe avec succès", HttpStatus.OK);
    }
}
