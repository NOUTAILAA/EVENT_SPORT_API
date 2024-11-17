package com.sport.app.service.servicesImp;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Regle;
import com.sport.app.repository.RegleRepository;
import com.sport.app.service.services.RegleService;
=======
import com.sport.app.entity.Regle;
import com.sport.app.repository.RegleRepository;
import com.sport.app.service.services.RegleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec

@Service
public class RegleServiceImpl implements RegleService {

    private final RegleRepository regleRepository;

    @Autowired
    public RegleServiceImpl(RegleRepository regleRepository) {
        this.regleRepository = regleRepository;
    }

    @Override
    public List<Regle> getAllRegles() {
        return regleRepository.findAll();
    }

    @Override
    public Regle getRegleById(Long id) {
        Optional<Regle> regle = regleRepository.findById(id);
        if (regle.isPresent()) {
            return regle.get();
        } else {
            throw new RuntimeException("Règle non trouvée pour l'id : " + id);
        }
    }

    @Override
    public Regle createRegle(Regle regle) {
        return regleRepository.save(regle);
    }

    @Override
    public Regle updateRegle(Long id, Regle regle) {
        Regle existingRegle = getRegleById(id);
        existingRegle.setDescription(regle.getDescription());
        return regleRepository.save(existingRegle);
    }

    @Override
    public void deleteRegle(Long id) {
        regleRepository.deleteById(id);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec
