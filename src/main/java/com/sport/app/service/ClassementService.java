package com.sport.app.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Classement;
import com.sport.app.entity.ClassementEquipeSimple;
import com.sport.app.entity.ClassementSimple;
import com.sport.app.entity.Evenement;
import com.sport.app.entity.Resultat;
import com.sport.app.entity.TypeDeSport;
import com.sport.app.repository.ClassementRepository;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.ResultatRepository;

@Service
public class ClassementService {

    @Autowired
    private ClassementRepository classementRepository;

    @Autowired
    private EvenementRepository evenementRepository;
    @Autowired
    private ResultatRepository resultatRepository;

    public List<Classement> findByEvenement(Long evenementId) {
        Evenement evenement = evenementRepository.findById(evenementId).orElseThrow(() -> new RuntimeException("Evenement not found"));
        return classementRepository.findAllByEvenement(evenement);
    }

    public Classement saveClassement(Classement classement) {
        return classementRepository.save(classement);
    }

    public List<Classement> findClassementsByEvenementSorted(Long evenementId) {
        return classementRepository.findClassementsByEvenementIdSorted(evenementId);
    }
    public List<Resultat> calculerClassementParTemps(Long evenementId) {
        return resultatRepository.findResultatsByEvenementIdSortedByTemps(evenementId);
    }
    public List<ClassementSimple> getSimpleClassementByEvenement(Long evenementId) {
        List<Resultat> resultats =resultatRepository.findResultatsByEvenementIdSortedByTemps(evenementId);
        return resultats.stream()
            .map(resultat -> new ClassementSimple(
                resultat.getParticipant().getId(),
                resultat.getParticipant().getName(),
                resultat.getTemps()))
            .collect(Collectors.toList());
    
}
    public List<Classement> findClassementsByEvenementSortedByButs(Long evenementId) {
        List<Resultat> resultats = resultatRepository.findByEvenementIdSortedByButs(evenementId);
        return resultats.stream()
            .map(resultat -> new Classement(
                resultat.getEvenement(),
                resultat.getEquipe(),
                null, // Pas de participant spécifique pour le score d'équipe
                resultat.getNombreButs()))
            .collect(Collectors.toList());
    }
    public List<ClassementEquipeSimple> getSimpleClassementEquipeByEvenement(Long evenementId) {
        List<Resultat> resultats = resultatRepository.findByEvenementIdSortedByButs(evenementId);
        return resultats.stream()
            .collect(Collectors.groupingBy(Resultat::getEquipe, Collectors.summingInt(Resultat::getNombreButs)))
            .entrySet().stream()
            .map(entry -> new ClassementEquipeSimple(
                entry.getKey().getId(),
                entry.getKey().getNom(),
                entry.getValue()))
            .sorted(Comparator.comparing(ClassementEquipeSimple::getScoreTotal).reversed())
            .collect(Collectors.toList());
    }

    
    
    
    public List<Object> getClassementGlobalParTypeDeSportId(Long typeSportId) {
        List<Resultat> resultats = resultatRepository.findByTypeDeSportId(typeSportId);
        if (resultats.isEmpty()) {
            return new ArrayList<>();  // Retourner une liste vide ou gérer l'absence de résultats
        }

        TypeDeSport typeDeSport = resultats.get(0).getEvenement().getTypeDeSport();

        if (typeDeSport.getNombreParticipantsParEquipe() > 1) {
            // Gestion de plusieurs participants par équipe
            return resultats.stream()
                .filter(resultat -> resultat.getEquipe() != null)
                .collect(Collectors.groupingBy(Resultat::getEquipe, Collectors.summingInt(Resultat::getNombreButs)))
                .entrySet().stream()
                .map(entry -> new ClassementEquipeSimple(
                    entry.getKey().getId(),
                    entry.getKey().getNom(),
                    entry.getValue()))
                .sorted(Comparator.comparing(ClassementEquipeSimple::getScoreTotal).reversed())
                .collect(Collectors.toList());
        } else {
            // Gestion d'un seul participant par équipe (individuel)
            return resultats.stream()
                .filter(resultat -> resultat.getParticipant() != null)
                .map(resultat -> new ClassementSimple(
                    resultat.getParticipant().getId(),
                    resultat.getParticipant().getName(),
                    resultat.getTemps()))
                .sorted(Comparator.comparing(ClassementSimple::getTemps)) // Tri par temps croissant
                .collect(Collectors.toList());
        }
    }



}
