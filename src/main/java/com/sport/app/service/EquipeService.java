package com.sport.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Equipe;
import com.sport.app.entity.Evenement;
import com.sport.app.entity.Participant;
import com.sport.app.repository.EquipeRepository;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.ParticipantRepository;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EvenementRepository evenementRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    public Equipe creerEquipe(Long evenementId, Equipe equipe) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        
        equipe.setEvenement(evenement);
        return equipeRepository.save(equipe);
    }

    public Equipe obtenirEquipe(Long equipeId) {
        return equipeRepository.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée"));
    }

    public List<Equipe> obtenirEquipesParEvenement(Long evenementId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        return evenement.getEquipes();
    }

    public void ajouterParticipantDansEquipe(Long equipeId, Long participantId) {
        Equipe equipe = obtenirEquipe(equipeId);
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

        if (equipe.getParticipants().size() >= equipe.getEvenement().getTypeDeSport().getNombreParticipantsParEquipe()) {
            throw new RuntimeException("Équipe complète");
        }

        equipe.ajouterParticipant(participant);
        equipeRepository.save(equipe);
    }
}
