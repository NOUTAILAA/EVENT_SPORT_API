package com.sport.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Participant;
import com.sport.app.repository.ParticipantRepository;
import com.sport.app.service.ParticipantService;

@RestController
@RequestMapping("/participants")
@CrossOrigin(origins = "http://localhost:4200")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;
    @Autowired
    private ParticipantRepository participantRepository;



    @GetMapping("/{participantId}")
    public ResponseEntity<Participant> obtenirParticipant(@PathVariable Long participantId) {
        Participant participant = participantService.obtenirParticipant(participantId);
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

    @GetMapping("/liste")
    public List<Participant> obtenirTousLesParticipants() {
        List<Participant> participants = participantRepository.findAll();
        participants.forEach(p -> System.out.println(p.toString())); // Log les données
        return participants;
    }
    @GetMapping("/listeSimple")
    public ResponseEntity<List<Map<String, Object>>> obtenirParticipantsSimples() {
        List<Participant> participants = participantService.obtenirTousLesParticipants(); // Récupérer tous les participants

        // Transformer la liste des participants en une liste de maps simples
        List<Map<String, Object>> simpleParticipants = participants.stream().map(participant -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", participant.getId());
            map.put("name", participant.getName());
            map.put("email", participant.getEmail());
            map.put("telephone", participant.getTelephone());
            map.put("password", participant.getPassword());
            return map;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(simpleParticipants, HttpStatus.OK);
    }


    @DeleteMapping("/{participantId}")
    public ResponseEntity<String> supprimerParticipant(@PathVariable Long participantId) {
        participantService.supprimerParticipant(participantId);
        return new ResponseEntity<>("Participant supprimé avec succès", HttpStatus.OK);
    }
    @PutMapping("/{participantId}")
    public ResponseEntity<Participant> mettreAJourParticipant(
            @PathVariable Long participantId, 
            @RequestBody Participant participantDetails) {
        Participant participantMisAJour = participantService.mettreAJourParticipant(participantId, participantDetails);
        return new ResponseEntity<>(participantMisAJour, HttpStatus.OK);
    }
    @PostMapping("/creer")
    public ResponseEntity<Participant> creerParticipant(@RequestBody Participant participant) {
        Participant nouveauParticipant = participantService.creerParticipant(participant);
        return new ResponseEntity<>(nouveauParticipant, HttpStatus.CREATED);
    }

    @GetMapping("/verify/{id}")
    public ResponseEntity<String> verifyParticipant(@PathVariable Long id) {
        boolean isVerified = participantService.verifyParticipant(id);
        if (isVerified) {
            return new ResponseEntity<>("Compte vérifié avec succès", HttpStatus.OK);
        }
        return new ResponseEntity<>("Participant non trouvé", HttpStatus.BAD_REQUEST);
    }


}
