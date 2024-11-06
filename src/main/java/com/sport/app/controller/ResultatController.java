package com.sport.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sport.app.entity.Resultat;
import com.sport.app.service.ResultatService;

import java.util.List;
@RestController
@RequestMapping("/api/resultats")
public class ResultatController {

    @Autowired
    private ResultatService resultatService;

    // Ajouter un résultat pour une équipe
    @PostMapping("/ajouter/equipe")
    public Resultat ajouterResultatEquipe(
            @RequestParam Long evenementId,
            @RequestParam Long equipeId,
            @RequestParam int nombreButs) {
        return resultatService.ajouterResultatEquipe(evenementId, equipeId, nombreButs);
    }

    // Ajouter un résultat pour un participant (marathon)
    @PostMapping("/ajouter/participant")
    public Resultat ajouterResultatParticipant(
            @RequestParam Long evenementId,
            @RequestParam Long participantId,
            @RequestParam double temps) {
        return resultatService.ajouterResultatParticipant(evenementId, participantId, temps);
    }

    // Obtenir les résultats d'un événement
    @GetMapping("/evenement/{evenementId}")
    public List<Resultat> obtenirResultatsParEvenement(@PathVariable Long evenementId) {
        return resultatService.obtenirResultatsParEvenement(evenementId);
    }
}
