package com.sport.app.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Evenement;
import com.sport.app.entity.Localisation;
import com.sport.app.entity.Participant;
import com.sport.app.entity.Promotion;
import com.sport.app.entity.TypeDeSport;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.PromotionRepository;
import com.sport.app.service.EvenementService;
import com.sport.app.service.LocalisationService;
import com.sport.app.service.TypeDeSportService;

@RestController
@RequestMapping("/evenements")
@CrossOrigin(origins = "http://localhost:4200")
public class EvenementController {
	 @Autowired
	    private EvenementRepository evenementRepository;
	 @Autowired
	    private PromotionRepository promotionRepository;

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private TypeDeSportService typeDeSportService;

    @Autowired
    private LocalisationService localisationService;
    // CREER EVENT
    @PostMapping("/creer")
    public ResponseEntity<Evenement> creerEvenement(@RequestBody Map<String, Object> evenementData) {
        try {
            String nom = (String) evenementData.get("nom");
            Double prix = Double.valueOf(evenementData.get("prix").toString());
            Long localisationId = Long.valueOf(evenementData.get("localisationId").toString());
            Long typeDeSportId = Long.valueOf(evenementData.get("typeDeSportId").toString());
            String dateString = (String) evenementData.get("date");
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

            Localisation localisation = localisationService.findById(localisationId);
            TypeDeSport typeDeSport = typeDeSportService.findById(typeDeSportId);

            Evenement nouvelEvenement = new Evenement();
            nouvelEvenement.setNom(nom);
            nouvelEvenement.setPrix(prix);  
            nouvelEvenement.setDate(date);  
            nouvelEvenement.setLocalisation(localisation);
            nouvelEvenement.setTypeDeSport(typeDeSport);

        
            
            evenementService.save(nouvelEvenement);
            return new ResponseEntity<>(nouvelEvenement, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


// POUR AJOUTER DES PARTICIPANTS A DES EVENEMENTS
    @PostMapping("/{evenementId}/inscrire/{participantId}")
    public ResponseEntity<Map<String, String>> inscrireParticipant(
            @PathVariable Long evenementId, @PathVariable Long participantId) {
        Map<String, String> response = new HashMap<>();
        try {
            boolean inscrit = evenementService.inscrireParticipant(evenementId, participantId);
            if (inscrit) {
                response.put("message", "Participant inscrit avec succès");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Échec de l'inscription");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            // Renvoyer le message d'erreur précis
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

// LISTER TOUS LES EVENEMENTS pour admin
    @GetMapping("/liste")
    public ResponseEntity<List<Evenement>> obtenirTousLesEvenements() {
        List<Evenement> evenements = evenementService.obtenirTousLesEvenements();
        return new ResponseEntity<>(evenements, HttpStatus.OK);
    } 
    // pour admin un affichage simple
    @GetMapping("/listeSimple")
    public ResponseEntity<List<Map<String, Object>>> obtenirEvenementsSimples() {
        List<Evenement> evenements = evenementService.obtenirTousLesEvenements();

        // Filtrer pour ne garder que les événements vérifiés
        List<Evenement> evenementsVerifies = evenements.stream()
                .filter(Evenement::isVerified)  // Filtre basé sur l'attribut 'verified'
                .collect(Collectors.toList());

        List<Map<String, Object>> simpleEvenements = evenementsVerifies.stream().map(evenement -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", evenement.getId());
            map.put("nom", evenement.getNom());
            map.put("prix", evenement.getPrix());
            map.put("date", evenement.getDate());
            map.put("typeDeSportId", evenement.getTypeDeSport() != null ? evenement.getTypeDeSport().getId() : null);
            map.put("localisationId", evenement.getLocalisation() != null ? evenement.getLocalisation().getId() : null);

            // Ajouter les IDs des équipes et des participants de chaque équipe
            List<Map<String, Object>> equipeInfos = evenement.getEquipes().stream().map(equipe -> {
                Map<String, Object> equipeMap = new HashMap<>();
                equipeMap.put("equipeId", equipe.getId());
                List<Long> participantIds = equipe.getParticipants().stream()
                        .map(Participant::getId)
                        .collect(Collectors.toList());
                equipeMap.put("participantIds", participantIds);
                return equipeMap;
            }).collect(Collectors.toList());
            map.put("equipes", equipeInfos);

            // Ajouter les résultats de l'événement
            List<Map<String, Object>> resultatsInfos = evenement.getResultats().stream().map(resultat -> {
                Map<String, Object> resultatMap = new HashMap<>();
                resultatMap.put("resultatId", resultat.getId());
                resultatMap.put("equipeId", resultat.getEquipe() != null ? resultat.getEquipe().getId() : null);
                resultatMap.put("participantId", resultat.getParticipant() != null ? resultat.getParticipant().getId() : null);
                resultatMap.put("nombreButs", resultat.getNombreButs());
                resultatMap.put("temps", resultat.getTemps());
                return resultatMap;
            }).collect(Collectors.toList());
            map.put("resultats", resultatsInfos);

            return map;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(simpleEvenements, HttpStatus.OK);
    }



    /// cas de many to many 
    @PostMapping("/{evenementId}/equipes/{equipeId}/ajouter-participant/{participantId}")
    public ResponseEntity<String> ajouterParticipantEquipe(
            @PathVariable Long evenementId,
            @PathVariable Long equipeId,
            @PathVariable Long participantId) {
        
        evenementService.ajouterParticipantEquipe(evenementId, equipeId, participantId);
        return new ResponseEntity<>("Participant ajouté à l'équipe avec succès", HttpStatus.OK);
    }
    
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenirEvenementAvecDetails(@PathVariable Long id) {
        Evenement evenement = evenementService.findEventById(id);

        // Construire une réponse avec les détails de l'événement, participants, et équipes
        Map<String, Object> response = new HashMap<>();
        response.put("id", evenement.getId());
        response.put("nom", evenement.getNom());
        response.put("date" , evenement.getDate());

        response.put("localisationId", evenement.getLocalisation().getId());
        response.put("typeDeSportId", evenement.getTypeDeSport().getId());
        response.put("prix", evenement.getPrix());

        // Ajouter les équipes et leurs participants
        List<Map<String, Object>> equipeDetails = evenement.getEquipes().stream().map(equipe -> {
            Map<String, Object> equipeMap = new HashMap<>();
            equipeMap.put("equipeId", equipe.getId());
            equipeMap.put("nomEquipe", equipe.getNom());
            equipeMap.put("participants", equipe.getParticipants().stream().map(participant -> {
                Map<String, Object> participantMap = new HashMap<>();
                participantMap.put("id", participant.getId());
                participantMap.put("name", participant.getName());
                participantMap.put("email", participant.getEmail());
                return participantMap;
            }).collect(Collectors.toList()));
            return equipeMap;
        }).collect(Collectors.toList());

        response.put("equipes", equipeDetails);
        // Ajouter les résultats de l'événement
        List<Map<String, Object>> resultatsInfos = evenement.getResultats().stream().map(resultat -> {
            Map<String, Object> resultatMap = new HashMap<>();
            resultatMap.put("resultatId", resultat.getId());
            resultatMap.put("equipeId", resultat.getEquipe() != null ? resultat.getEquipe().getId() : null);
            resultatMap.put("participantId", resultat.getParticipant() != null ? resultat.getParticipant().getName() : null);
            resultatMap.put("nombreButs", resultat.getNombreButs());
            resultatMap.put("temps", resultat.getTemps());
            return resultatMap;
        }).collect(Collectors.toList());
        response.put("resultats", resultatsInfos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


 
    // ceci affichera pour chaque participant connecté les evenements avec leur prix apres reduction ===> donc on doit utiliser ceci pour afficher la liste des evenements participés pour le participant
    
    @GetMapping("/prixReduits")
    public ResponseEntity<List<Map<String, Object>>> getEvenementsAvecPrixReduits(@RequestParam Long participantId) {
        try {
            List<Map<String, Object>> evenementsAvecPrix = evenementService.obtenirEvenementsAvecPrixReduits(participantId);
            return ResponseEntity.ok(evenementsAvecPrix);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
 

 // LES EQUIPES ALEATOIIRESSS ( facultatif càd qu'on peut ne pas l'utiliser)
    @PostMapping("/{evenementId}/repartir")
    public ResponseEntity<String> repartirParticipantsAleatoirement(@PathVariable Long evenementId) {
        evenementService.repartirParticipantsAleatoirement(evenementId);
        return new ResponseEntity<>("Participants répartis aléatoirement", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evenement> mettreAJourEvenement(@PathVariable Long id, @RequestBody Evenement evenementDetails) {
        System.out.println("Reçu une requête PUT pour mettre à jour l'événement avec ID : " + id);
        System.out.println("Localisation reçue : " + evenementDetails.getLocalisation());
        try {
            Evenement evenementMisAJour = evenementService.mettreAJourEvenement(id, evenementDetails);
            return new ResponseEntity<>(evenementMisAJour, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.err.println("Erreur : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping("/nonComplets")
    public ResponseEntity<List<Map<String, Object>>> obtenirEvenementsNonComplets() {
        List<Evenement> evenementsNonComplets = evenementService.obtenirEvenementsNonComplets();
        List<Map<String, Object>> simpleEvenements = evenementsNonComplets.stream().map(evenement -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", evenement.getId());
            map.put("nom", evenement.getNom());
            map.put("date", evenement.getDate());
            map.put("prix", evenement.getPrix());
            map.put("typeDeSportId", evenement.getTypeDeSport() != null ? evenement.getTypeDeSport().getId() : null);
            map.put("localisationId", evenement.getLocalisation() != null ? evenement.getLocalisation().getId() : null);

            map.put("nombreParticipantsActuels", evenement.getParticipants().size());
            map.put("nombreParticipantsMax", evenement.getTypeDeSport().getNombreEquipesMax() * evenement.getTypeDeSport().getNombreParticipantsParEquipe());
            return map;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(simpleEvenements, HttpStatus.OK);
    }
    @GetMapping("/nonCompletsNonParticipes")
    public ResponseEntity<List<Map<String, Object>>> obtenirEvenementsNonCompletsEtNonParticipes(
            @RequestParam Long participantId) {
        try {
            List<Map<String, Object>> evenements = evenementService.obtenirEvenementsNonCompletsEtNonParticipes(participantId);
            return ResponseEntity.ok(evenements);
        } catch (RuntimeException e) {
            // Gérer les erreurs, si nécessaire
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    //les événements du participant
    @GetMapping("/participes/{participantId}")
    public ResponseEntity<List<Map<String, Object>>> obtenirEvenementsParticipes(@PathVariable Long participantId) {
        try {
            List<Map<String, Object>> evenementsParticipes = evenementService.obtenirEvenementsParticipes(participantId);
            return ResponseEntity.ok(evenementsParticipes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/nouveaux")
    public ResponseEntity<List<Map<String, Object>>> obtenirEvenementsAVenir() {
        List<Evenement> evenementsAVenir = evenementService.obtenirEvenementsAVenir();
        List<Map<String, Object>> simpleEvenements = evenementsAVenir.stream().map(evenement -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", evenement.getId());
            map.put("nom", evenement.getNom());
            map.put("date", evenement.getDate());
            map.put("prix", evenement.getPrix());
            map.put("typeDeSport", evenement.getTypeDeSport() != null ? evenement.getTypeDeSport().getNom() : "Non défini");
            map.put("localisation", evenement.getLocalisation() != null ? evenement.getLocalisation().getVille() : "Inconnu");
            return map;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(simpleEvenements);
    }

    
    @PostMapping("/{evenementId}/appliquerPromotion/{participantId}")
    public ResponseEntity<Map<String, Object>> appliquerPromotion(
            @PathVariable Long evenementId,
            @PathVariable Long participantId,
            @RequestParam String codePromo) {

        try {
            double prixReduit = evenementService.appliquerPromotion(evenementId, participantId, codePromo);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Promotion appliquée avec succès");
            response.put("prixApresRemise", prixReduit);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/promotions")
    public ResponseEntity<List<Promotion>> obtenirToutesLesPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/participes-prix-reduits/{participantId}")
    public ResponseEntity<List<Map<String, Object>>> obtenirEvenementsParticipesAvecPrixReduit(
            @PathVariable Long participantId) {
        List<Map<String, Object>> evenements = evenementService.obtenirEvenementsParticipesAvecPrixReduit(participantId);
        return ResponseEntity.ok(evenements);
    }
    @PostMapping("/creerParParticipant/{participantId}")
    public ResponseEntity<Map<String, Object>> creerEvenementParParticipant(
            @PathVariable Long participantId,
            @RequestBody Map<String, Object> evenementData) {
        Map<String, Object> response = new HashMap<>();

        try {
            String nom = (String) evenementData.get("nom");
            Double prix = Double.valueOf(evenementData.get("prix").toString());
            Long localisationId = Long.valueOf(evenementData.get("localisationId").toString());
            Long typeDeSportId = Long.valueOf(evenementData.get("typeDeSportId").toString());
            String dateString = (String) evenementData.get("date");
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

            Localisation localisation = localisationService.findById(localisationId);
            TypeDeSport typeDeSport = typeDeSportService.findById(typeDeSportId);
            Participant participant = evenementService.findParticipantById(participantId);

            // Créer un nouvel événement
            Evenement nouvelEvenement = new Evenement();
            nouvelEvenement.setNom(nom);
            nouvelEvenement.setPrix(prix);
            nouvelEvenement.setDate(date);
            nouvelEvenement.setLocalisation(localisation);
            nouvelEvenement.setTypeDeSport(typeDeSport);
            
            // Définir comme non vérifié
            nouvelEvenement.setVerified(false);

            // Sauvegarder l'événement
            evenementService.save(nouvelEvenement);

            // Ajouter le créateur à la liste des participants
            evenementService.inscrireParticipant(nouvelEvenement.getId(), participantId);

            response.put("message", "Événement créé avec succès et participant ajouté");
            response.put("evenementId", nouvelEvenement.getId());
            response.put("verified", nouvelEvenement.isVerified());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Erreur lors de la création de l'événement : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/listeSimpleNonVerified")
    public ResponseEntity<List<Map<String, Object>>> obtenirEvenementsSimplesNonVerifies() {
        List<Evenement> evenements = evenementService.obtenirTousLesEvenements();

        // Filtrer pour ne garder que les événements non vérifiés
        List<Evenement> evenementsNonVerifies = evenements.stream()
                .filter(evenement -> !evenement.isVerified())  // Filtre basé sur l'attribut 'verified' à false
                .collect(Collectors.toList());

        List<Map<String, Object>> simpleEvenements = evenementsNonVerifies.stream().map(evenement -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", evenement.getId());
            map.put("nom", evenement.getNom());
            map.put("prix", evenement.getPrix());
            map.put("date", evenement.getDate());
            map.put("typeDeSportId", evenement.getTypeDeSport() != null ? evenement.getTypeDeSport().getId() : null);
            map.put("localisationId", evenement.getLocalisation() != null ? evenement.getLocalisation().getId() : null);

            // Ajouter les IDs des équipes et des participants de chaque équipe
            List<Map<String, Object>> equipeInfos = evenement.getEquipes().stream().map(equipe -> {
                Map<String, Object> equipeMap = new HashMap<>();
                equipeMap.put("equipeId", equipe.getId());
                List<Long> participantIds = equipe.getParticipants().stream()
                        .map(Participant::getId)
                        .collect(Collectors.toList());
                equipeMap.put("participantIds", participantIds);
                return equipeMap;
            }).collect(Collectors.toList());
            map.put("equipes", equipeInfos);

            // Ajouter les résultats de l'événement
            List<Map<String, Object>> resultatsInfos = evenement.getResultats().stream().map(resultat -> {
                Map<String, Object> resultatMap = new HashMap<>();
                resultatMap.put("resultatId", resultat.getId());
                resultatMap.put("equipeId", resultat.getEquipe() != null ? resultat.getEquipe().getId() : null);
                resultatMap.put("participantId", resultat.getParticipant() != null ? resultat.getParticipant().getId() : null);
                resultatMap.put("nombreButs", resultat.getNombreButs());
                resultatMap.put("temps", resultat.getTemps());
                return resultatMap;
            }).collect(Collectors.toList());
            map.put("resultats", resultatsInfos);

            return map;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(simpleEvenements, HttpStatus.OK);
    }
    @PutMapping("/{evenementId}/verify")
    public ResponseEntity<Map<String, String>> verifierEvenement(@PathVariable Long evenementId) {
        try {
            evenementService.verifierEvenement(evenementId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Événement vérifié avec succès.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    
    
    
    // Modif de prix nom et date de event
    @PutMapping("/{id}/modifier")
    public ResponseEntity<Map<String, String>> modifierEvenement(
            @PathVariable Long id,
            @RequestBody Map<String, Object> modifications) {

        try {
            Evenement evenementModifie = evenementService.modifierDetailsEvenement(id, modifications);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Événement modifié avec succès.");
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Événement modifié, mais l'envoi d'email a échoué.");
            return ResponseEntity.ok(response);  // ✅ Retourne 200 même si l'email échoue
        }
    }



}