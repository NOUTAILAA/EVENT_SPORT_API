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

import com.sport.app.entity.Evenement;
import com.sport.app.entity.Promotion;
import com.sport.app.service.EvenementService;
import com.sport.app.service.services.PromotionService;

@RestController
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;
    @Autowired
    private PromotionService promotionService;
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
// LISTER TOUS LES EVENEMENTS
    @GetMapping("/liste")
    public ResponseEntity<List<Evenement>> obtenirTousLesEvenements() {
        List<Evenement> evenements = evenementService.obtenirTousLesEvenementsAvecPrix();
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
        Evenement evenement = evenementService.obtenirEvenementParIdAvecPrix(id);
        return new ResponseEntity<>(evenement, HttpStatus.OK);
    } 
    // un evenement avec le prix par exemple de 120 dh et le piurcentage de code promo est 20 docn il doit s'afficher 96 
    @PostMapping("/{eventId}/apply-promo")
    public ResponseEntity<Evenement> applyPromoCode(@PathVariable Long eventId, @RequestBody String promoCode) {
        System.out.println("Tentative d'application du code promo: " + promoCode);

        Evenement event = evenementService.findEventById(eventId);
        if (event == null) {
            System.out.println("Événement non trouvé pour l'ID: " + eventId);
            return ResponseEntity.notFound().build();
        }

        Promotion promotion = promotionService.findByCode(promoCode);
        if (promotion == null) {
            System.out.println("Code promo invalide: " + promoCode);
            return ResponseEntity.badRequest().body(null);
        }

        // Calcul du prix avec réduction
        double discount = promotion.getDiscountPercentage() / 100.0;
        double newPrice = event.getPrix() * (1 - discount);
        event.setPrix(newPrice);

        // Sauvegarde de l'événement avec le nouveau prix
        evenementService.saveEvenement(event);
        System.out.println("Prix de l'événement après application de la promotion: " + event.getPrix());

        return ResponseEntity.ok(event);
    }


}
