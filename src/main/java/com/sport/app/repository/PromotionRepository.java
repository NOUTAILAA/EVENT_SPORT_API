package com.sport.app.repository;

import com.sport.app.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
<<<<<<< HEAD
    Promotion findByCode(String code);
    boolean existsByCode(String code);

}
=======
}
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec
