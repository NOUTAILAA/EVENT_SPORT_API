package com.sport.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Evenement;
import com.sport.app.service.EvenementService;

@RestController
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;
    // CREER EVENT
    @PostMapping("/creer")
    public ResponseEntity<Evenement> creerEvenement(@RequestBody Evenement evenement) {
        Evenement nouvelEvenement = evenementService.creerEvenement(evenement);
        return new ResponseEntity<>(nouvelEvenement, HttpStatus.CREATED);
    }
// POUR AJOUTER DES PARTICIPANTS A DES EVENEMENTS
    @PostMapping("/{evenementId}/inscrire/{participantId}")
    public ResponseEntity<String> inscrireParticipant(@PathVariable Long evenementId, @PathVariable Long participantId) {
        boolean inscrit = evenementService.inscrireParticipant(evenementId, participantId);
        if (inscrit) {
            return new ResponseEntity<>("Participant inscrit avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Échec de l'inscription", HttpStatus.BAD_REQUEST);
        }
    }
// LES EQUIPES ALEATOIIRESSS ( facultatif)
    @PostMapping("/{evenementId}/repartir")
    public ResponseEntity<String> repartirParticipantsAleatoirement(@PathVariable Long evenementId) {
        evenementService.repartirParticipantsAleatoirement(evenementId);
        return new ResponseEntity<>("Participants répartis aléatoirement", HttpStatus.OK);
    }
// LISTER TOUS LES EVENEMENTS pour admin
    @GetMapping("/liste")
    public ResponseEntity<List<Evenement>> obtenirTousLesEvenements() {
        List<Evenement> evenements = evenementService.obtenirTousLesEvenements();
        return new ResponseEntity<>(evenements, HttpStatus.OK);
    }

    /// hada li khdam b many to many
    
    @PostMapping("/{evenementId}/equipes/{equipeId}/ajouter-participant/{participantId}")
    public ResponseEntity<String> ajouterParticipantEquipe(
            @PathVariable Long evenementId,
            @PathVariable Long equipeId,
            @PathVariable Long participantId) {
        
        evenementService.ajouterParticipantEquipe(evenementId, equipeId, participantId);
        return new ResponseEntity<>("Participant ajouté à l'équipe avec succès", HttpStatus.OK);
    }
    
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Evenement> obtenirEvenementParId(@PathVariable Long id) {
        Evenement evenement = evenementService.findEventById(id);
        return new ResponseEntity<>(evenement, HttpStatus.OK);
    } 

 
    // ceci affichera pour chaque participant connectés les evenements avec leur prix apres reduction
    
    @GetMapping("/prixReduits")
    public ResponseEntity<List<Map<String, Object>>> getEvenementsAvecPrixReduits(@RequestParam Long participantId) {
        try {
            List<Map<String, Object>> evenementsAvecPrix = evenementService.obtenirEvenementsAvecPrixReduits(participantId);
            return ResponseEntity.ok(evenementsAvecPrix);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
 


}
