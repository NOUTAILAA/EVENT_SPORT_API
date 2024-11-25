package com.sport.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Evenement;
import com.sport.app.entity.Localisation;
import com.sport.app.entity.Participant;
import com.sport.app.entity.TypeDeSport;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.service.EvenementService;
import com.sport.app.service.LocalisationService;
import com.sport.app.service.TypeDeSportService;

@RestController
@RequestMapping("/evenements")
public class EvenementController {
	 @Autowired
	    private EvenementRepository evenementRepository;

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
            nouvelEvenement.setDate(date); // Set the date

            nouvelEvenement.setLocalisation(localisation);
            nouvelEvenement.setTypeDeSport(typeDeSport);

            evenementService.save(nouvelEvenement);
            return new ResponseEntity<>(nouvelEvenement, HttpStatus.CREATED);
        } catch (Exception e) {
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
        List<Map<String, Object>> simpleEvenements = evenements.stream().map(evenement -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", evenement.getId());
            map.put("nom", evenement.getNom());
            map.put("prix", evenement.getPrix());
            map.put("date" , evenement.getDate());
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
   
}
