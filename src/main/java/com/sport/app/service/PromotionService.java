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

  /*  public double appliquerPromotion(Long evenementId, Long participantId, String codePromo) {
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
    } */

    // Créer un nouveau code promo
    public Promotion creerPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    // Obtenir tous les codes promos
    public List<Promotion> obtenirTousLesPromotions() {
        return promotionRepository.findAll();
    }
    public double appliquerPromotion(Long evenementId, Long participantId, String codePromo) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));

        Promotion promo = promotionRepository.findByCode(codePromo)
                .orElseThrow(() -> new RuntimeException("Code promo invalide"));

        double prixInitial = evenement.getPrix();
        double remise = promo.getRemise();
        double prixAvecRemise = prixInitial - (prixInitial * remise / 100);

        // Mettre à jour et sauvegarder le prix après remise
        evenementRepository.save(evenement);

        return prixAvecRemise;
    }


 // Méthode pour modifier une promotion existante
    public Promotion modifierPromotion(Long id, Promotion promotion) {
        // Vérifier si la promotion existe déjà
        Promotion promotionExistante = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion non trouvée avec l'ID: " + id));

        // Modifier les champs de la promotion existante
        promotionExistante.setCode(promotion.getCode());
        promotionExistante.setRemise(promotion.getRemise());

        // Sauvegarder la promotion mise à jour
        return promotionRepository.save(promotionExistante);
    }
    // Méthode pour supprimer une promotion
    public void supprimerPromotion(Long id) {
        // Vérifier si la promotion existe
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion non trouvée avec l'ID: " + id));
        
        // Supprimer la promotion
        promotionRepository.delete(promotion);
    }
}
