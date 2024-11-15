package com.sport.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Participant;
import com.sport.app.service.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @PostMapping("/creer")
    public ResponseEntity<Participant> creerParticipant(@RequestBody Participant participant) {
        Participant nouveauParticipant = participantService.creerParticipant(participant);
        return new ResponseEntity<>(nouveauParticipant, HttpStatus.CREATED);
    }

    @GetMapping("/{participantId}")
    public ResponseEntity<Participant> obtenirParticipant(@PathVariable Long participantId) {
        Participant participant = participantService.obtenirParticipant(participantId);
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

    @GetMapping("/liste")
    public ResponseEntity<List<Participant>> obtenirTousLesParticipants() {
        List<Participant> participants = participantService.obtenirTousLesParticipants();
        return new ResponseEntity<>(participants, HttpStatus.OK);
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
}
