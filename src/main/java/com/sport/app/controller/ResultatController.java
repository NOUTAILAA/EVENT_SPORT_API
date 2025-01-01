package com.sport.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Resultat;
import com.sport.app.service.ResultatService;
@RestController
@RequestMapping("/api/resultats")
public class ResultatController {

    @Autowired
    private ResultatService resultatService;
    // ceci est correct
    @PostMapping("/ajouter/equipe/{evenementId}")
    public Resultat ajouterResultatEquipe(
            @PathVariable Long evenementId,
            @RequestParam Long equipeId,
            @RequestParam int nombreButs,
            @RequestParam(required = false) Double temps) {  // temps peut être optionnel
        return resultatService.ajouterResultatEquipe(evenementId, equipeId, nombreButs, temps);
    }

    // Ajouter un résultat pour une équipe 
    @PostMapping("/equiipe")
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
    
    
    
    
    @GetMapping("/classement/simplifie/evenement/{evenementId}")
    public List<Map<String, Object>> getClassementSimplifieParEvenement(@PathVariable Long evenementId) {
        return resultatService.obtenirClassementSimplifieParEvenement(evenementId);
    }

    @GetMapping("/participant/{participantId}")
    public List<Map<String, Object>> obtenirResultatsParParticipant(@PathVariable Long participantId) {
        return resultatService.obtenirResultatsSimplifiesParParticipant(participantId);
    }

    
    
    
    // obtenir le classement des equipes d'un eveneme
    @GetMapping("/classement/evenement/{evenementId}")
    public List<Resultat> getClassementParEvenement(@PathVariable Long evenementId) {
        return resultatService.obtenirClassementParEvenement(evenementId);
    }
    @GetMapping("/classement/global/type-de-sport/{typeDeSportId}")
    public List<String> getClassementGlobalParTypeDeSport(@PathVariable Long typeDeSportId) {
        return resultatService.obtenirClassementGlobalParTypeDeSport(typeDeSportId);
    }

}
