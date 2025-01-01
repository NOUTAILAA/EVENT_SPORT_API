package com.sport.app.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        
        return evenement.getResultats().stream()
                .filter(r -> r != null) // Ensure no null Resultat objects
                .sorted((r1, r2) -> {
                    // Compare by nombreButs (descending)
                    int compareButs = Integer.compare(r2.getNombreButs(), r1.getNombreButs());
                    if (compareButs != 0) {
                        return compareButs;
                    }
                    // Compare by temps (ascending) if nombreButs are equal
                    if (r1.getTemps() != null && r2.getTemps() != null) {
                        return Double.compare(r1.getTemps(), r2.getTemps());
                    }
                    return 0;
                })
                .collect(Collectors.toList());
    }
    
    
    
    
    
    public List<Map<String, Object>> obtenirClassementSimplifieParEvenement(Long evenementId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));

        return evenement.getResultats().stream()
                .filter(resultat -> resultat.getEquipe() != null) // Filtrer uniquement les résultats avec des équipes
                .map(resultat -> {
                    Map<String, Object> info = new HashMap<>();
                    info.put("equipeNom", resultat.getEquipe() != null ? resultat.getEquipe().getId() : "Aucune équipe");
                    info.put("participants", resultat.getEquipe().getParticipants().stream()
                            .map(participant -> participant.getName()) // Récupérer uniquement les noms des participants
                            .collect(Collectors.toList())); // Transformer en liste de noms
                    info.put("nombreButs", resultat.getNombreButs());
                    info.put("temps", resultat.getTemps());
                    return info;
                })
                .collect(Collectors.toList());
    }


 // Classement global par type de sport
    public List<String> obtenirClassementGlobalParTypeDeSport(Long typeDeSportId) {
        // Récupérer tous les événements pour le type de sport donné
        List<Evenement> evenements = evenementRepository.findByTypeDeSportId(typeDeSportId);

        if (evenements.isEmpty()) {
            throw new RuntimeException("Aucun événement trouvé pour ce type de sport.");
        }

        // Trier les événements par un critère personnalisé
        return evenements.stream()
                .sorted((e1, e2) -> {
                    // Comparaison basée sur le nombre total de buts (ou d'autres critères si nécessaire)
                    int totalButsE1 = e1.getResultats().stream().mapToInt(Resultat::getNombreButs).sum();
                    int totalButsE2 = e2.getResultats().stream().mapToInt(Resultat::getNombreButs).sum();

                    // Trier par total de buts décroissant
                    return Integer.compare(totalButsE2, totalButsE1);
                })
                .map(Evenement::getNom) // Récupérer uniquement les noms des événements
                .collect(Collectors.toList());
    }


    public List<Resultat> obtenirResultatsParParticipant(Long participantId) {
        return resultatRepository.findByParticipantId(participantId);
    }
    
    // Récupérer les résultats (individuels et par équipe) d'un participant
    public List<Map<String, Object>> obtenirResultatsSimplifiesParParticipant(Long participantId) {
        List<Resultat> resultatsIndividuels = resultatRepository.findByParticipantId(participantId);

        // Récupérer les équipes auxquelles appartient le participant
        List<Equipe> equipes = equipeRepository.findByParticipantsId(participantId);

        // Récupérer les résultats de ces équipes
        List<Resultat> resultatsEquipes = equipes.stream()
                .flatMap(equipe -> equipe.getResultats().stream())
                .collect(Collectors.toList());

        // Fusionner les résultats individuels et ceux des équipes
        List<Resultat> tousLesResultats = resultatsIndividuels;
        tousLesResultats.addAll(resultatsEquipes);

        // Formatter les résultats pour simplifier la réponse
        return tousLesResultats.stream()
                .map(resultat -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("evenementNom", resultat.getEvenement().getNom());

                    // Vérifier si le résultat est lié à un participant direct
                    if (resultat.getParticipant() != null && resultat.getParticipant().getId().equals(participantId)) {
                        map.put("resultat", formatResultat(resultat));
                        map.put("type", "Participant Individuel");
                    } 
                    // Vérifier si le participant est dans l'équipe
                    else if (resultat.getEquipe() != null) {
                        boolean estDansEquipe = resultat.getEquipe().getParticipants().stream()
                                .anyMatch(p -> p.getId().equals(participantId));

                        if (estDansEquipe) {
                            map.put("resultat", formatResultat(resultat));
                            map.put("type", "Participant en Équipe");
                        }
                    }
                    return map;
                })
                .collect(Collectors.toList());
    }

    // Formater le résultat pour afficher nombre de buts et temps
    private String formatResultat(Resultat resultat) {
        String buts = resultat.getNombreButs() + " buts";
        String temps = resultat.getTemps() != null ? resultat.getTemps() + " secondes" : "N/A";

        return buts + " | " + temps;  // Afficher les deux valeurs
    }
}