package com.sport.app.service.servicesImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Promotion;
import com.sport.app.repository.PromotionRepository;
import com.sport.app.service.services.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion getPromotionById(Long id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found with id: " + id));
    }

    public Promotion createPromotion(Promotion promotion) {
        // Vérifier si un code promo identique existe déjà
        if (promotionRepository.existsByCode(promotion.getCode())) {
            throw new RuntimeException("Le code promo existe déjà.");
        }
        
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updatePromotion(Long id, Promotion promotion) {
        Promotion existingPromotion = getPromotionById(id);
        existingPromotion.setCode(promotion.getCode());
        existingPromotion.setDiscountPercentage(promotion.getDiscountPercentage());
        existingPromotion.setValidUntil(promotion.getValidUntil());
        return promotionRepository.save(existingPromotion);
    }

    @Override
    public void deletePromotion(Long id) {
        if (!promotionRepository.existsById(id)) {
            throw new RuntimeException("Promotion not found with id: " + id);
        }
        promotionRepository.deleteById(id);
    }
    
    @Override
    public Promotion findByCode(String code) {
        return promotionRepository.findByCode(code);
    }
}