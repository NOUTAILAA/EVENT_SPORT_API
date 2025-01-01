package com.sport.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Evenement;
import com.sport.app.entity.Promotion;
import com.sport.app.repository.PromotionParticipantEvenementRepository;
import com.sport.app.service.EvenementService;
import com.sport.app.service.PromotionService;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;
    @Autowired
    private EvenementService evenementService;
    @Autowired
    PromotionParticipantEvenementRepository promotionParticipantEvenementRepository;
    // Endpoint pour créer un nouveau code promo
    @PostMapping
    public ResponseEntity<Promotion> creerPromotion(@RequestBody Promotion promotion) {
        Promotion nouvellePromotion = promotionService.creerPromotion(promotion);
        return ResponseEntity.ok(nouvellePromotion);
    }

    // Endpoint pour appliquer un code promo pour un participant et un événement
  /*  @PostMapping("/appliquer")
    public ResponseEntity<Double> appliquerPromotion(
            @RequestParam Long evenementId,
            @RequestParam Long participantId,
            @RequestParam String codePromo) {
        double prixAvecRemise = promotionService.appliquerPromotion(evenementId, participantId, codePromo);
        return ResponseEntity.ok(prixAvecRemise);
    } */
    @PutMapping("/{id}")
    public ResponseEntity<Promotion> modifierPromotion(@PathVariable Long id, @RequestBody Promotion promotion) {
        Promotion promotionModifiee = promotionService.modifierPromotion(id, promotion);
        return ResponseEntity.ok(promotionModifiee);
    }
    // Endpoint pour récupérer tous les codes promos
    @GetMapping
    public ResponseEntity<List<Promotion>> obtenirTousLesCodesPromos() {
        List<Promotion> promotions = promotionService.obtenirTousLesPromotions();
        return ResponseEntity.ok(promotions);
    }
    @PostMapping("/appliquer")
    public ResponseEntity<Double> appliquerPromotion(
            @RequestParam Long evenementId,
            @RequestParam Long participantId,
            @RequestParam String codePromo) {

        double prixAvecRemise = promotionService.appliquerPromotion(evenementId, participantId, codePromo);

        if (prixAvecRemise > 0) {
            Evenement evenement = evenementService.findEventById(evenementId);
            evenementService.save(evenement);
        }

        return ResponseEntity.ok(prixAvecRemise);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPromotion(@PathVariable Long id) {
        promotionService.supprimerPromotion(id);
        return ResponseEntity.noContent().build();
    }
    
    
    
    @GetMapping("/verifier-promo")
    public ResponseEntity<Boolean> verifierPromoDejaAppliquee(
            @RequestParam Long evenementId,
            @RequestParam Long participantId) {
        
        boolean promoAppliquee = promotionParticipantEvenementRepository
                .findByEvenementIdAndParticipantId(evenementId, participantId)
                .isPresent();

        return ResponseEntity.ok(promoAppliquee);
    }
}
