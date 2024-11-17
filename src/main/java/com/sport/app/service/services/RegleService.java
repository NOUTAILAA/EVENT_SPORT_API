package com.sport.app.service.services;

import com.sport.app.entity.Regle;
import java.util.List;

public interface RegleService {
    List<Regle> getAllRegles();
    Regle getRegleById(Long id);
    Regle createRegle(Regle regle);
    Regle updateRegle(Long id, Regle regle);
    void deleteRegle(Long id);
<<<<<<< HEAD
}
=======
}
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec
