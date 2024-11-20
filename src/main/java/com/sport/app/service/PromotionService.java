package com.sport.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Evenement;
import com.sport.app.entity.Participant;
import com.sport.app.entity.Promotion;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.ParticipantRepository;
import com.sport.app.repository.PromotionRepository;
@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public double appliquerPromotion(Long evenementId, Long participantId, String codePromo) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

        if (!evenement.getParticipants().contains(participant)) {
            throw new RuntimeException("Le participant n'est pas inscrit à cet événement.");
        }

        Promotion promotion = promotionRepository.findByCode(codePromo)
                .orElseThrow(() -> new RuntimeException("Code promo invalide ou inexistant."));

        // Vérifier si le participant a déjà utilisé ce code promo
        if (promotion.getParticipants().contains(participant)) {
            throw new RuntimeException("Le participant a déjà utilisé ce code promo.");
        }

        // Ajouter le participant à la liste des utilisateurs du code promo
        promotion.getParticipants().add(participant);
        promotionRepository.save(promotion);

        // Calculer le prix réduit
        double prixInitial = evenement.getPrix();
        double prixAvecRemise = prixInitial - (prixInitial * promotion.getRemise() / 100);

        return prixAvecRemise; // Retourner le prix réduit uniquement pour ce participant
    }

    // Créer un nouveau code promo
    public Promotion creerPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    // Obtenir tous les codes promos
    public List<Promotion> obtenirTousLesPromotions() {
        return promotionRepository.findAll();
    }

   
}
