package com.sport.app.repository;

import com.sport.app.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Promotion findByCode(String code);
    boolean existsByCode(String code);

}