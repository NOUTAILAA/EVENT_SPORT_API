package com.sport.app.service;
import java.util.List;
import java.util.stream.Collectors;

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
    public Resultat ajouterResultatEquipe(Long evenementId, Long equipeId, int nombreButs, Double temps) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        Equipe equipe = equipeRepository.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée"));

        // Vérifier si l'équipe appartient à l'événement
        if (!evenement.getEquipes().contains(equipe)) {
            throw new RuntimeException("L'équipe spécifiée n'appartient pas à l'événement donné.");
        }

        // Créer le résultat
        Resultat resultat = new Resultat(nombreButs, temps, equipe, null, evenement);
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
    
    
 // Classement basé sur les résultats pour un événement donné
    public List<Resultat> obtenirClassementParEvenement(Long evenementId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        
        // Trier les résultats par nombre de buts décroissant, ou par temps croissant
        return evenement.getResultats().stream()
                .sorted((r1, r2) -> {
                    // Comparaison personnalisée selon le type de résultat
                    if (r1.getNombreButs() != r2.getNombreButs()) {
                        return Integer.compare(r2.getNombreButs(), r1.getNombreButs()); // Buts décroissants
                    }
                    if (r1.getTemps() != null && r2.getTemps() != null) {
                        return Double.compare(r1.getTemps(), r2.getTemps()); // Temps croissants
                    }
                    return 0;
                })
                .collect(Collectors.toList());
    }
 // Classement global par type de sport
    public List<Resultat> obtenirClassementGlobalParTypeDeSport(Long typeDeSportId) {
        // Récupérer tous les événements pour le type de sport donné
        List<Evenement> evenements = evenementRepository.findByTypeDeSportId(typeDeSportId);

        if (evenements.isEmpty()) {
            throw new RuntimeException("Aucun événement trouvé pour ce type de sport.");
        }
 // Collecter tous les résultats pour ces événements
    List<Resultat> resultats = evenements.stream()
            .flatMap(evenement -> evenement.getResultats().stream())
            .collect(Collectors.toList());

    // Trier les résultats globalement
    return resultats.stream()
            .sorted((r1, r2) -> {
                // Comparaison personnalisée selon le type de résultat
                if (r1.getNombreButs() != r2.getNombreButs()) {
                    return Integer.compare(r2.getNombreButs(), r1.getNombreButs()); // Buts décroissants
                }
                if (r1.getTemps() != null && r2.getTemps() != null) {
                    return Double.compare(r1.getTemps(), r2.getTemps()); // Temps croissants
                }
                return 0;
            })
            .collect(Collectors.toList());
}
}
