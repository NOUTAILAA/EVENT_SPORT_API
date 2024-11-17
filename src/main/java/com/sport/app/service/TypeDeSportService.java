package com.sport.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.TypeDeSport;
import com.sport.app.repository.TypeDeSportRepository;

@Service
public class TypeDeSportService {

    @Autowired
    private TypeDeSportRepository typeDeSportRepository;

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

            // Mettre à jour les règles
            typeExistant.getRegles().clear(); // Supprimer les anciennes règles
            typeExistant.getRegles().addAll(typeDeSport.getRegles()); // Ajouter les nouvelles règles

            // Sauvegarder et retourner le type mis à jour
            return typeDeSportRepository.save(typeExistant);
        } else {
            throw new RuntimeException("Type de sport non trouvé");
        }
    }


<<<<<<< HEAD
}
=======
}
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec
