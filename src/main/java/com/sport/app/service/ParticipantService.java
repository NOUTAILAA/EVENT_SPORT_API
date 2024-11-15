package com.sport.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Participant;
import com.sport.app.entity.ResourceNotFoundException;
import com.sport.app.repository.ParticipantRepository;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public Participant creerParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    public Participant obtenirParticipant(Long participantId) {
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé"));
    }

    public List<Participant> obtenirTousLesParticipants() {
        return participantRepository.findAll();
    }

    public void supprimerParticipant(Long participantId) {
        participantRepository.deleteById(participantId);
    }
    public Participant mettreAJourParticipant(Long participantId, Participant participantDetails) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant introuvable avec l'ID : " + participantId));
        participant.setName(participantDetails.getName());
        participant.setEmail(participantDetails.getEmail());
        participant.setPassword(participantDetails.getPassword());
        participant.setTelephone(participantDetails.getTelephone());

        // Mettez à jour d'autres champs si nécessaire

        return participantRepository.save(participant);
    }
}
