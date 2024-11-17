package com.sport.app.service.services;

import com.sport.app.entity.Promotion;

import java.util.List;

public interface PromotionService {
    List<Promotion> getAllPromotions();
    Promotion getPromotionById(Long id);
    Promotion createPromotion(Promotion promotion);
    Promotion updatePromotion(Long id, Promotion promotion);
    void deletePromotion(Long id);
<<<<<<< HEAD
    Promotion findByCode(String code);
=======
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec
}