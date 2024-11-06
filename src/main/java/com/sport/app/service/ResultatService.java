package com.sport.app.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Equipe;
import com.sport.app.entity.Evenement;
import com.sport.app.entity.Participant;
import com.sport.app.entity.Resultat;
import com.sport.app.repository.EquipeRepository;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.ParticipantRepository;
import com.sport.app.repository.ResultatRepository;

@Service
public class ResultatService {

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    // Ajouter un résultat pour un sport d'équipe
    public Resultat ajouterResultatEquipe(Long evenementId, Long equipeId, int nombreButs) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        Equipe equipe = equipeRepository.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée"));

        Resultat resultat = new Resultat(nombreButs, 0, equipe, null, evenement);
        return resultatRepository.save(resultat);
    }

    // Ajouter un résultat pour un sport individuel
    public Resultat ajouterResultatParticipant(Long evenementId, Long participantId, double temps) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

        Resultat resultat = new Resultat(0, temps, null, participant, evenement);
        return resultatRepository.save(resultat);
    }

    public List<Resultat> obtenirResultatsParEvenement(Long evenementId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        return evenement.getResultats();
    }
}
