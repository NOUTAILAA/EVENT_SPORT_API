package com.sport.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Regle;
import com.sport.app.entity.TypeDeSport;
import com.sport.app.repository.RegleRepository;
import com.sport.app.repository.TypeDeSportRepository;

@Service
public class TypeDeSportService {

    @Autowired
    private TypeDeSportRepository typeDeSportRepository;
    @Autowired
    private RegleRepository regleRepository;

    public TypeDeSport creerTypeDeSport(TypeDeSport typeDeSport) {
        return typeDeSportRepository.save(typeDeSport);
    }

    public TypeDeSport obtenirTypeDeSport(Long typeDeSportId) {
        return typeDeSportRepository.findById(typeDeSportId)
                .orElseThrow(() -> new RuntimeException("Type de sport non trouvé"));
    }

    public List<TypeDeSport> obtenirTousLesTypesDeSport() {
        return typeDeSportRepository.findAll();
    }

    public boolean verifierValiditePourEvenement(Long typeDeSportId, int nombreEquipes, int nombreParticipants) {
        TypeDeSport typeDeSport = obtenirTypeDeSport(typeDeSportId);
        return typeDeSport.estValidePourEvenement(nombreEquipes, nombreParticipants);
    }
    public TypeDeSport mettreAJourTypeDeSport(Long id, TypeDeSport typeDeSport) {
        // Trouver le typeDeSport existant
        TypeDeSport typeExistant = obtenirTypeDeSport(id);

        if (typeExistant != null) {
            // Mettre à jour les attributs de base
            typeExistant.setNom(typeDeSport.getNom());
            typeExistant.setNombreEquipesMax(typeDeSport.getNombreEquipesMax());
            typeExistant.setNombreParticipantsParEquipe(typeDeSport.getNombreParticipantsParEquipe());

            // Vérifier et initialiser la collection des règles si elle est null
            if (typeExistant.getRegles() == null) {
                typeExistant.setRegles(new HashSet<>());  // ou un ArrayList si vous utilisez une liste
            }

            // Mettre à jour les règles
            typeExistant.getRegles().addAll(typeDeSport.getRegles()); // Ajouter les nouvelles règles

            // Sauvegarder et retourner le type mis à jour
            return typeDeSportRepository.save(typeExistant);
        } else {
            throw new RuntimeException("Type de sport non trouvé");
        }
    }

    public TypeDeSport findById(Long id) {
        return typeDeSportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Type de Sport not found with ID: " + id));
    }
    public TypeDeSport ajouterReglesAuTypeDeSport(Long typeDeSportId, List<Long> regleIds) {
        // Trouver le TypeDeSport
        TypeDeSport typeDeSport = typeDeSportRepository.findById(typeDeSportId)
                .orElseThrow(() -> new RuntimeException("Type de sport non trouvé"));

        // Récupérer les règles via leurs IDs
        List<Regle> regles = regleRepository.findAllById(regleIds);

        // Ajouter les nouvelles règles aux règles existantes (sans supprimer les anciennes)
        if (typeDeSport.getRegles() == null) {
            typeDeSport.setRegles(new HashSet<>());
        }

        // Ajouter les règles à l'ensemble de règles existantes
        typeDeSport.getRegles().addAll(regles);  // Cela ajoute les nouvelles règles sans effacer les anciennes

        // Sauvegarder le TypeDeSport avec les règles mises à jour
        return typeDeSportRepository.save(typeDeSport);
    }
    /**
     * Dissocier une règle d'un TypeDeSport spécifique
     * @param typeDeSportId ID du TypeDeSport
     * @param regleId ID de la règle à dissocier
     * @return Le TypeDeSport mis à jour après la dissociation
     */
    public TypeDeSport dissocierRegleDuTypeDeSport(Long typeDeSportId, Long regleId) {
        // Trouver le TypeDeSport
        TypeDeSport typeDeSport = typeDeSportRepository.findById(typeDeSportId)
                .orElseThrow(() -> new RuntimeException("Type de sport non trouvé"));

        // Trouver la règle à dissocier
        Regle regle = regleRepository.findById(regleId)
                .orElseThrow(() -> new RuntimeException("Règle non trouvée"));

        // Vérifier si la règle est bien associée au TypeDeSport
        if (typeDeSport.getRegles() != null && typeDeSport.getRegles().contains(regle)) {
            // Supprimer la règle de l'ensemble de règles du TypeDeSport
            typeDeSport.getRegles().remove(regle);
            // Sauvegarder le TypeDeSport mis à jour
            return typeDeSportRepository.save(typeDeSport);
        } else {
            throw new RuntimeException("La règle n'est pas associée à ce type de sport");
        }
    }
}
