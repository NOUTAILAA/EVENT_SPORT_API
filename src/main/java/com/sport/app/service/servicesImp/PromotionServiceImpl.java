package com.sport.app.service.servicesImp;

import com.sport.app.entity.Promotion;
import com.sport.app.repository.PromotionRepository;
import com.sport.app.service.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Promotion createPromotion(Promotion promotion) {
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
}
