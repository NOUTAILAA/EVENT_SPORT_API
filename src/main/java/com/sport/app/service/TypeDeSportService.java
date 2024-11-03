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
        // Trouver le typeDeSport existant, mettre à jour ses attributs, et le sauvegarder
        TypeDeSport typeExistant = obtenirTypeDeSport(id);
        if (typeExistant != null) {
            typeExistant.setNom(typeDeSport.getNom());
            typeExistant.setNombreEquipesMax(typeDeSport.getNombreEquipesMax());
            typeExistant.setNombreParticipantsParEquipe(typeDeSport.getNombreParticipantsParEquipe());
            return typeDeSportRepository.save(typeExistant);
        }
        return null; // Ou lancez une exception si l'objet n'existe pas
    }

}
