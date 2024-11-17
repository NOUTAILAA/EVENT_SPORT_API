package com.sport.app.controller;

<<<<<<< HEAD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Promotion;
import com.sport.app.service.services.PromotionService;
=======
import com.sport.app.entity.Promotion;
import com.sport.app.service.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec

@RestController
@RequestMapping("/api/promotions")
@CrossOrigin("http://localhost:4200")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping
    public List<Promotion> getAllPromotions() {
        return promotionService.getAllPromotions();
    }

    @GetMapping("/{id}")
    public Promotion getPromotionById(@PathVariable Long id) {
        return promotionService.getPromotionById(id);
    }

<<<<<<< HEAD
  
    @PostMapping("/create")
    public ResponseEntity<String> createPromotion(@RequestBody Promotion promotion) {
        try {
            promotionService.createPromotion(promotion);
            return ResponseEntity.status(HttpStatus.CREATED).body("Promotion créée avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } }
=======
    @PostMapping
    public Promotion createPromotion(@RequestBody Promotion promotion) {
        return promotionService.createPromotion(promotion);
    }

>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec
    @PutMapping("/{id}")
    public Promotion updatePromotion(@PathVariable Long id, @RequestBody Promotion promotion) {
        return promotionService.updatePromotion(id, promotion);
    }

    @DeleteMapping("/{id}")
    public void deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec