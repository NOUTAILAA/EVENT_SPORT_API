package com.sport.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sport.app.entity.Promotion;
import com.sport.app.service.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    // Endpoint pour créer un nouveau code promo
    @PostMapping
    public ResponseEntity<Promotion> creerPromotion(@RequestBody Promotion promotion) {
        Promotion nouvellePromotion = promotionService.creerPromotion(promotion);
        return ResponseEntity.ok(nouvellePromotion);
    }

    // Endpoint pour appliquer un code promo pour un participant et un événement
    @PostMapping("/appliquer")
    public ResponseEntity<Double> appliquerPromotion(
            @RequestParam Long evenementId,
            @RequestParam Long participantId,
            @RequestParam String codePromo) {
        double prixAvecRemise = promotionService.appliquerPromotion(evenementId, participantId, codePromo);
        return ResponseEntity.ok(prixAvecRemise);
    }

    // Endpoint pour récupérer tous les codes promos
    @GetMapping
    public ResponseEntity<List<Promotion>> obtenirTousLesCodesPromos() {
        List<Promotion> promotions = promotionService.obtenirTousLesPromotions();
        return ResponseEntity.ok(promotions);
    }
    
}
