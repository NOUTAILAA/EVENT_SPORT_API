package com.sport.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Evenement;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String name, Long participantId) {
        String link = "http://localhost:8090/participants/verify/" + participantId;
        String subject = "Vérification de votre compte";
        String body = "<p>Bonjour " + name + ",</p>"
                + "<p>Cliquez sur le lien ci-dessous pour vérifier votre compte :</p>"
                + "<a href=\"" + link + "\">Vérifier mon compte</a>"
                + "<p>Merci,</p><p>L'équipe Sport App</p>";

        sendHtmlEmail(toEmail, subject, body);
    }

    public void sendHtmlEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Échec de l'envoi de l'e-mail", e);
        }
}
    

    public void envoyerEmailModificationEvenement(String toEmail, String name, Evenement evenement) {
        String subject = "Mise à jour de l'événement : " + evenement.getNom();
        String body = "<p>Bonjour " + name + ",</p>"
                + "<p>L'événement auquel vous êtes associé a été mis à jour.</p>"
                + "<p><strong>Nouveau nom :</strong> " + evenement.getNom() + "</p>"
                + "<p><strong>Date :</strong> " + evenement.getDate() + "</p>"
                + "<p><strong>Prix :</strong> " + evenement.getPrix() + " €</p>"
                + "<p>Merci de prendre note de ces changements.</p>"
                + "<p>L'équipe Sport App</p>";

        sendHtmlEmail(toEmail, subject, body);
    }
 // Envoi de l'e-mail avec le mot de passe temporaire
    public void sendTemporaryPasswordEmail(String toEmail, String name, String newPassword) {
        String subject = "Réinitialisation de votre mot de passe";
        String body = "<p>Bonjour " + name + ",</p>"
                + "<p>Votre nouveau mot de passe temporaire est : <strong>" + newPassword + "</strong></p>"
                + "<p>Nous vous recommandons de le changer après connexion.</p>"
                + "<p>L'équipe Sport App</p>";

        sendHtmlEmail(toEmail, subject, body);
    }
}
