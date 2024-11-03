package com.sport.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Evenement;
import com.sport.app.entity.Participant;
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

    public List<Evenement> obtenirTousLesEvenements() {
        return evenementRepository.findAll();
    }
}
