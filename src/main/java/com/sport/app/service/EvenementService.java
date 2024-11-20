package com.sport.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Equipe;
import com.sport.app.entity.Evenement;
import com.sport.app.entity.Participant;
import com.sport.app.entity.Promotion;
import com.sport.app.repository.EquipeRepository;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.ParticipantRepository;

@Service
public class EvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    public Evenement creerEvenement(Evenement evenement) {
        // Logique de création de l'événement
        return evenementRepository.save(evenement);
    }

    public boolean inscrireParticipant(Long evenementId, Long participantId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

        // Essayer d'ajouter le participant en respectant les règles définies par l'événement
        if (!evenement.ajouterParticipant(participant)) {
            throw new RuntimeException("L'événement est complet ou le participant ne peut pas être ajouté.");
        }
        if (evenement.getParticipants().contains(participant)) {
            throw new RuntimeException("Le participant est déjà inscrit à cet événement.");
        }
        evenement.getParticipants().add(participant);

        evenementRepository.save(evenement);
        return true;
    }


    public void repartirParticipantsAleatoirement(Long evenementId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        
        List<Participant> participants = evenement.getParticipants();
        evenement.repartirParticipantsAleatoirement(participants);
        evenementRepository.save(evenement);
    }
    
    
// Hada li khdam b many to many
    public List<Evenement> obtenirTousLesEvenements() {
        return evenementRepository.findAll();
    }
    public void ajouterParticipantEquipe(Long evenementId, Long equipeId, Long participantId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        Equipe equipe = equipeRepository.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée"));
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

        if (!equipe.getEvenement().equals(evenement)) {
            throw new RuntimeException("L'équipe ne fait pas partie de cet événement.");
        }

        equipe.ajouterParticipant(participant);
        equipeRepository.save(equipe);
    }
    public Evenement obtenirEvenementParIdAvecPrix(Long id) {
        Evenement evenement = evenementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        
        double prixAvecPromotion = calculerPrixAvecPromotion(evenement);
        evenement.setPrix(prixAvecPromotion); // Met à jour le prix avec le prix final

        return evenement;
    }

    public double calculerPrixAvecPromotion(Evenement evenement) {
        if (evenement.getPromotions() == null || evenement.getPromotions().isEmpty()) {
            return evenement.getPrix();
        }

        double prixFinal = evenement.getPrix();
        for (Promotion promo : evenement.getPromotions()) {
            if (promo.getValidUntil().after(new Date())) {
                prixFinal -= prixFinal * (promo.getDiscountPercentage() / 100);
            }
        }
        return prixFinal;
    }
    
    
    
    // hada howa dial api /liste
    public List<Evenement> obtenirTousLesEvenementsAvecPrix() {
        List<Evenement> evenements = evenementRepository.findAll();
        for (Evenement evenement : evenements) {
            double prixAvecPromotion = calculerPrixAvecPromotion(evenement);
            evenement.setPrix(prixAvecPromotion); // Met à jour le prix dans l'objet
        }
        return evenements;
    }

    // Méthode pour trouver un événement par ID
    public Evenement findEventById(Long eventId) {
        return evenementRepository.findById(eventId).orElse(null);
    }

    // Méthode pour sauvegarder ou mettre à jour un événement
    public Evenement saveEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    
}
