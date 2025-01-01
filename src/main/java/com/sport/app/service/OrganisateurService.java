package com.sport.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Evenement;
import com.sport.app.entity.Organisateur;
import com.sport.app.repository.EvenementRepository;
import com.sport.app.repository.OrganisateurRepository;

@Service
public class OrganisateurService {

    @Autowired
    private OrganisateurRepository organisateurRepository;

    @Autowired
    private EvenementRepository evenementRepository;

    public Organisateur creerOrganisateur(Organisateur organisateur) {
        return organisateurRepository.save(organisateur);
    }

    public Organisateur obtenirOrganisateur(Long organisateurId) {
        return organisateurRepository.findById(organisateurId)
                .orElseThrow(() -> new RuntimeException("Organisateur non trouvé"));
    }

    public List<Evenement> obtenirEvenementsOrganises(Long organisateurId) {
        Organisateur organisateur = obtenirOrganisateur(organisateurId);
        return organisateur.getEvenementsOrganises();
    }
}
