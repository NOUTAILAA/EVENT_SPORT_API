package com.sport.app.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Organisateur;
import com.sport.app.entity.Participant;
import com.sport.app.entity.User;
import com.sport.app.repository.OrganisateurRepository;
import com.sport.app.repository.ParticipantRepository;
@Service
public class AuthService {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OrganisateurRepository organisateurRepository;

    public User authenticate(String email, String password) {
        // Vérifier d'abord les participants
        User participant = participantRepository.findByEmailAndPassword(email, password);
        if (participant != null) {
            if (!participant.isVerified()) {
                throw new RuntimeException(
                    "Compte non verifie. Veuillez verifier votre compte en cliquant sur le lien envoye a votre adresse e-mail."
                );
            }
            return participant;
        }

        // Vérifier ensuite l'organisateur (sans vérification)
        User organisateur = organisateurRepository.findByEmailAndPassword(email, password);
        if (organisateur != null) {
            return organisateur;
        }

        // Retourner null si aucun utilisateur trouvé
        return null;
    }

private String generateRandomPassword() {
    String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    String digits = "0123456789";
    String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    String allChars = upperCase + lowerCase + digits + specialChars;

    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder();

    // Ajouter au moins un caractère de chaque type pour la complexité
    password.append(upperCase.charAt(random.nextInt(upperCase.length())));
    password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
    password.append(digits.charAt(random.nextInt(digits.length())));
    password.append(specialChars.charAt(random.nextInt(specialChars.length())));

    // Remplir le reste du mot de passe de manière aléatoire
    for (int i = 4; i < 12; i++) {  // Longueur totale de 12 caractères
        password.append(allChars.charAt(random.nextInt(allChars.length())));
    }

    // Mélanger les caractères pour éviter un modèle fixe
    return shuffleString(password.toString());
}

// Méthode pour mélanger les caractères
private String shuffleString(String input) {
    List<Character> characters = new ArrayList<>();
    for (char c : input.toCharArray()) {
        characters.add(c);
    }
    Collections.shuffle(characters);
    StringBuilder shuffled = new StringBuilder();
    for (char c : characters) {
        shuffled.append(c);
    }
    return shuffled.toString();
}

    // Réinitialisation du mot de passe
    public void forgotPassword(String email) {
        User user = participantRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Aucun compte associé à cet e-mail");
        }

        // Générer un mot de passe temporaire
        String newPassword = generateRandomPassword();

        // Envoyer l'e-mail
        emailService.sendTemporaryPasswordEmail(user.getEmail(), user.getName(), newPassword);

        // Mettre à jour le mot de passe dans la base de données (vous pouvez aussi hasher le mot de passe ici)
        user.setPassword(newPassword);
        if (user instanceof Participant) {
            participantRepository.save((Participant) user);
        } else if (user instanceof Organisateur) {
            organisateurRepository.save((Organisateur) user);
        }
    }
}
