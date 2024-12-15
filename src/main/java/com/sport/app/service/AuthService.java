package com.sport.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.User;
import com.sport.app.repository.ParticipantRepository;
import com.sport.app.repository.OrganisateurRepository;

@Service
public class AuthService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private OrganisateurRepository organisateurRepository;

    public User authenticate(String email, String password) {
        // Vérifier d'abord les participants
        User participant = participantRepository.findByEmailAndPassword(email, password);
        if (participant != null) {
            return participant;
        }

        // Vérifier ensuite l'organisateur
        User organisateur = organisateurRepository.findByEmailAndPassword(email, password);
        if (organisateur != null) {
            return organisateur;
        }

        // Si aucun utilisateur trouvé, retourner null
        return null;
    }
}
