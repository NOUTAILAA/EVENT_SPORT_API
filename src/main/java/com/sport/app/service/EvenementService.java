package com.sport.app.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Equipe;
import com.sport.app.entity.Evenement;
import com.sport.app.entity.Participant;
import com.sport.app.entity.Promotion;
import com.sport.app.entity.PromotionParticipantEvenement;
import com.sport.app.entity.Regle;
import com.sport.app.repository.EquipeRepository;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.ParticipantRepository;
import com.sport.app.repository.PromotionParticipantEvenementRepository;
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
    @Autowired
    private EmailService emailService;
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
        Evenement evenement = evenementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé avec l'ID : " + id));
        
        evenement.setNom(evenementDetails.getNom());
        evenement.setDate(evenementDetails.getDate());
        evenement.setPrix(evenementDetails.getPrix());
        evenement.setLocalisation(evenementDetails.getLocalisation());
        evenement.setTypeDeSport(evenementDetails.getTypeDeSport());

     

        return evenementRepository.save(evenement);
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
        Date dateActuelle = new Date();  // Date actuelle pour filtrer les événements futurs

        // Filtrer les événements non complets, non participés et vérifiés
        List<Evenement> evenementsFiltres = tousLesEvenements.stream()
                .filter(Evenement::isVerified)  // Filtrer par événements vérifiés
                .filter(evenement -> evenement.getParticipants().size() <  // Événement non complet
                        (evenement.getTypeDeSport().getNombreEquipesMax() * evenement.getTypeDeSport().getNombreParticipantsParEquipe()))
                .filter(evenement -> evenement.getParticipants().stream()  // Participant non inscrit
                        .noneMatch(participant -> participant.getId().equals(participantId)))
                .filter(evenement -> evenement.getDate().after(dateActuelle))  // Filtrer par date (événements futurs)
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

    
    
    
    
    public List<Map<String, Object>> obtenirEvenementsParticipes(Long participantId) {
        // Récupérer tous les événements où le participant est inscrit
        List<Evenement> evenements = evenementRepository.findAll().stream()
                .filter(Evenement::isVerified)  // Ajouter la condition pour les événements vérifiés
                .filter(evenement -> evenement.getParticipants().stream()
                        .anyMatch(participant -> participant.getId().equals(participantId)))
                .sorted(Comparator.comparing(Evenement::getDate)) // Tri par date ascendante
                .collect(Collectors.toList());

        // Transformer les événements en une liste simplifiée de Map
        return evenements.stream().map(evenement -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", evenement.getId());
            map.put("nom", evenement.getNom());
            map.put("date", evenement.getDate());
            map.put("prix", evenement.getPrix());
            map.put("typeDeSport", evenement.getTypeDeSport() != null ? evenement.getTypeDeSport().getNom() : "Non défini");
            map.put("localisation", evenement.getLocalisation() != null ? evenement.getLocalisation().getVille() : "Inconnu");

            // Ajouter les équipes et participants
            List<Map<String, Object>> equipeDetails = evenement.getEquipes().stream().map(equipe -> {
                Map<String, Object> equipeMap = new HashMap<>();
                equipeMap.put("equipeId", equipe.getId());
                equipeMap.put("nomEquipe", equipe.getNom());
                equipeMap.put("participants", equipe.getParticipants().stream()
                        .map(Participant::getName)
                        .collect(Collectors.toList()));
                return equipeMap;
            }).collect(Collectors.toList());
            map.put("equipes", equipeDetails);

            // Ajouter les résultats s'ils existent
            List<Map<String, Object>> resultats = evenement.getResultats().stream().map(resultat -> {
                Map<String, Object> resultatMap = new HashMap<>();
                resultatMap.put("resultatId", resultat.getId());
                resultatMap.put("equipeId", resultat.getEquipe() != null ? resultat.getEquipe().getNom() : "N/A");
                resultatMap.put("participantId", resultat.getParticipant() != null ? resultat.getParticipant().getName() : "N/A");
                resultatMap.put("nombreButs", resultat.getNombreButs());
                resultatMap.put("temps", resultat.getTemps());
                return resultatMap;
            }).collect(Collectors.toList());
            
            if (!resultats.isEmpty()) {
                map.put("resultats", resultats);
            }

            return map;
        }).collect(Collectors.toList());
    }

    
  
    
    public List<Map<String, Object>> obtenirEvenementsParticipesAvecPrixReduit(Long participantId) {
        List<Evenement> evenements = evenementRepository.findByParticipantsId(participantId);
        
        return evenements.stream().map(evenement -> {
        	Double prixApresRemise = promotionParticipantEvenementRepository
        		    .findByEvenementIdAndParticipantId(evenement.getId(), participantId)
        		    .map(ppe -> ppe.getPrixApresRemise())  // Use lambda to safely map
        		    .orElse(evenement.getPrix());

            Map<String, Object> map = new HashMap<>();
            map.put("id", evenement.getId());
            map.put("nom", evenement.getNom());
            map.put("date", evenement.getDate());
            map.put("prixInitial", evenement.getPrix());
            map.put("prixApresRemise", prixApresRemise);
            return map;
        }).collect(Collectors.toList());
    }
    
    
    public List<Evenement> obtenirEvenementsAVenir() {
        List<Evenement> tousLesEvenements = evenementRepository.findAll();
        Date dateActuelle = new Date();
        
        // Filtrer les événements dont la date est supérieure à aujourd'hui
        return tousLesEvenements.stream()
                .filter(Evenement::isVerified)  // Ajouter la condition pour les événements vérifiés

                .filter(evenement -> evenement.getDate().after(dateActuelle))
                .sorted(Comparator.comparing(Evenement::getDate))  // Trier par date
                .collect(Collectors.toList());
    }


    
    
    
    
    @Autowired
    private PromotionParticipantEvenementRepository promotionParticipantEvenementRepository;

    public double appliquerPromotion(Long evenementId, Long participantId, String codePromo) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));

        // Vérification si le code promo existe
        Promotion promotionAppliquee = promotionRepository.findByCode(codePromo)
                .orElseThrow(() -> new RuntimeException("Code promo invalide"));

        // Calcul du prix après remise
        double prixApresRemise = evenement.getPrix() - (evenement.getPrix() * promotionAppliquee.getRemise() / 100);

        // ✅ Utilisation de orElse pour éviter l'erreur
        PromotionParticipantEvenement entry = promotionParticipantEvenementRepository
                .findByEvenementIdAndParticipantId(evenementId, participantId)
                .orElse(new PromotionParticipantEvenement());

        // Mise à jour des champs
        entry.setEvenement(evenement);
        entry.setParticipant(participantRepository.findById(participantId).orElseThrow());
        entry.setPrixApresRemise(prixApresRemise);

        // Enregistrement de la mise à jour
        promotionParticipantEvenementRepository.save(entry);

        return prixApresRemise;
    }
    public Participant findParticipantById(Long participantId) {
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé avec l'ID : " + participantId));
    }

    public void verifierEvenement(Long evenementId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé avec ID : " + evenementId));

        if (evenement.isVerified()) {
            throw new RuntimeException("L'événement est déjà vérifié.");
        }

        evenement.setVerified(true);
        evenementRepository.save(evenement);
    }

    
    
    
    
    


    // Service : Modification des détails d'un événement
    public Evenement modifierDetailsEvenement(Long evenementId, Map<String, Object> modifications) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé avec ID : " + evenementId));

        // Appliquer les modifications (nom, prix, date)
        if (modifications.containsKey("nom")) {
            evenement.setNom(modifications.get("nom").toString());
        }
        if (modifications.containsKey("prix")) {
            evenement.setPrix(Double.valueOf(modifications.get("prix").toString()));
        }
        if (modifications.containsKey("date")) {
            try {
                Date nouvelleDate = new SimpleDateFormat("yyyy-MM-dd").parse(modifications.get("date").toString());
                evenement.setDate(nouvelleDate);
            } catch (Exception e) {
                throw new RuntimeException("Format de date invalide");
            }
        }

        evenementRepository.save(evenement);

        // Envoyer un email à chaque participant et aux participants des équipes organisatrices
        for (Participant participant : evenement.getParticipants()) {
            emailService.envoyerEmailModificationEvenement(participant.getEmail(), participant.getName(), evenement);
        }
        
        for (Equipe equipe : evenement.getEquipes()) {
            for (Participant participant : equipe.getParticipants()) {
                emailService.envoyerEmailModificationEvenement(participant.getEmail(), participant.getName(), evenement);
            }
        }

        return evenement;
    }



}