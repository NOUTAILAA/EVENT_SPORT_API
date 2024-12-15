package com.sport.app.service.servicesImp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Evenement;
import com.sport.app.entity.Regle;
import com.sport.app.entity.TypeDeSport;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.RegleRepository;
import com.sport.app.repository.TypeDeSportRepository;
import com.sport.app.service.services.RegleService;

@Service
public class RegleServiceImpl implements RegleService {

    private final RegleRepository regleRepository;
    @Autowired
    private TypeDeSportRepository typeDeSportRepository;
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
    @Override
    public List<Regle> getReglesByTypeDeSportId(Long typeDeSportId) {
        // Fetch rules where the TypeDeSport ID matches
        return regleRepository.findByTypesDeSportId(typeDeSportId);
    }
    public List<Regle> getReglesSansTypeDeSport(Long typeDeSportId) {
        // Récupérer le type de sport par son ID
        TypeDeSport typeDeSport = typeDeSportRepository.findById(typeDeSportId)
                .orElseThrow(() -> new RuntimeException("Type de sport non trouvé"));

        // Filtrer les règles qui ne sont pas associées à ce type de sport
        return regleRepository.findAll().stream()
                .filter(regle -> !regle.getTypesDeSport().contains(typeDeSport))
                .collect(Collectors.toList());
    }
    @Autowired
	private EvenementRepository evenementRepository;

	public List<Regle> getReglesByEvenementId(Long evenementId) {
	    Evenement evenement = evenementRepository.findById(evenementId)
	            .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
	    if (evenement.getTypeDeSport() != null) {
	        return new ArrayList<>(evenement.getTypeDeSport().getRegles());
	    }
	    return Collections.emptyList();
	}
}