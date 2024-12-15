package com.sport.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Equipe;
import com.sport.app.entity.Evenement;
import com.sport.app.entity.Localisation;
import com.sport.app.entity.Participant;
import com.sport.app.entity.Promotion;
import com.sport.app.entity.Regle;
import com.sport.app.entity.TypeDeSport;
import com.sport.app.repository.EquipeRepository;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.ParticipantRepository;
import com.sport.app.repository.PromotionRepository;

@Service
public class EvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EquipeRepository equipeRepository;
    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private LocalisationService localisationService;

    @Autowired
    private TypeDeSportService typeDeSportService;

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
            throw new RuntimeException("L event est complet ou le participant ne peut pas etre ajoute.");
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


 
    
    
    
   

    // Méthode pour trouver un événement par ID
    public Evenement findEventById(Long eventId) {
        return evenementRepository.findById(eventId).orElse(null);
    }

    // Méthode pour sauvegarder ou mettre à jour un événement
    public Evenement saveEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    
    
    

 // Méthode pour obtenir tous les événements avec les prix réduits pour un participant spécifique
    public List<Map<String, Object>> obtenirEvenementsAvecPrixReduits(Long participantId) {
        List<Evenement> evenements = evenementRepository.findAll();
        List<Map<String, Object>> evenementsAvecPrix = new ArrayList<>();

        for (Evenement evenement : evenements) {
            double prixReduit = getPrixWithPromotionsForParticipant(evenement.getId(), participantId);
            Map<String, Object> evenementDetails = new HashMap<>();
            evenementDetails.put("evenement", evenement);
            evenementDetails.put("prixReduit", prixReduit);
            evenementsAvecPrix.add(evenementDetails);
        }

        return evenementsAvecPrix;
    }

    private double getPrixWithPromotionsForParticipant(Long evenementId, Long participantId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));
        List<Promotion> appliedPromotions = promotionRepository.findByParticipantsId(participantId);
        double reducedPrice = evenement.getPrix();

        for (Promotion promotion : appliedPromotions) {
            if (promotion.getEvenements().contains(evenement)) {
                reducedPrice -= (reducedPrice * promotion.getRemise() / 100);
            }
        }

        return reducedPrice;
    }
    
    
    public Evenement save(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    public Evenement mettreAJourEvenement(Long id, Evenement evenementDetails) {
        System.out.println("Tentative de mise à jour de l'événement avec ID : " + id);
        Evenement evenementExistant = evenementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement introuvable avec l'ID : " + id));

        // Mise à jour des champs simples
        evenementExistant.setNom(evenementDetails.getNom());
        evenementExistant.setDate(evenementDetails.getDate());
        evenementExistant.setPrix(evenementDetails.getPrix());

        // Mise à jour de la localisation à partir de localisationId
        if (evenementDetails.getLocalisation() != null && evenementDetails.getLocalisation().getId() != null) {
            Localisation localisation = localisationService.findById(evenementDetails.getLocalisation().getId());
            evenementExistant.setLocalisation(localisation);
        }

        // Mise à jour du typeDeSport
        if (evenementDetails.getTypeDeSport() != null && evenementDetails.getTypeDeSport().getId() != null) {
            TypeDeSport typeDeSport = typeDeSportService.findById(evenementDetails.getTypeDeSport().getId());
            evenementExistant.setTypeDeSport(typeDeSport);
        }

        return evenementRepository.save(evenementExistant);
    }

    
    public List<Evenement> obtenirEvenementsNonComplets() {
        List<Evenement> tousLesEvenements = evenementRepository.findAll();
        return tousLesEvenements.stream()
                .filter(evenement -> evenement.getParticipants().size() < evenement.getTypeDeSport().getNombreEquipesMax() * evenement.getTypeDeSport().getNombreParticipantsParEquipe())
                .collect(Collectors.toList());
    }
    public List<Map<String, Object>> obtenirEvenementsNonCompletsEtNonParticipes(Long participantId) {
        // Récupérer tous les événements
        List<Evenement> tousLesEvenements = evenementRepository.findAll();

        // Filtrer les événements non complets et auxquels le participant ne participe pas
        List<Evenement> evenementsFiltres = tousLesEvenements.stream()
                .filter(evenement -> evenement.getParticipants().size() <
                        (evenement.getTypeDeSport().getNombreEquipesMax() * evenement.getTypeDeSport().getNombreParticipantsParEquipe())) // Événement non complet
                .filter(evenement -> evenement.getParticipants().stream()
                        .noneMatch(participant -> participant.getId().equals(participantId))) // Participant non inscrit
                .collect(Collectors.toList());

        // Transformer les événements filtrés en une liste d'objets simples (Map)
        return evenementsFiltres.stream().map(evenement -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", evenement.getId());
            map.put("nom", evenement.getNom());
            map.put("date", evenement.getDate());
            map.put("prix", evenement.getPrix());
            map.put("typeDeSportId", evenement.getTypeDeSport() != null ? evenement.getTypeDeSport().getId() : null);
            map.put("localisationId", evenement.getLocalisation() != null ? evenement.getLocalisation().getId() : null);
            map.put("nombreParticipantsActuels", evenement.getParticipants().size());
            map.put("nombreParticipantsMax", evenement.getTypeDeSport().getNombreEquipesMax() * evenement.getTypeDeSport().getNombreParticipantsParEquipe());

            // Inclure les règles associées au type de sport
            if (evenement.getTypeDeSport() != null) {
                List<String> reglesDescriptions = evenement.getTypeDeSport().getRegles().stream()
                        .map(Regle::getDescription)
                        .collect(Collectors.toList());
                map.put("regles", reglesDescriptions);
            } else {
                map.put("regles", Collections.emptyList());
            }

            return map;
        }).collect(Collectors.toList());
    }



}