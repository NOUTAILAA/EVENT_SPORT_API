package com.sport.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Evenement;
import com.sport.app.entity.Organisateur;
import com.sport.app.service.OrganisateurService;

@RestController
@RequestMapping("/organisateurs")
public class OrganisateurController {

    @Autowired
    private OrganisateurService organisateurService;

    @PostMapping("/creer")
    public ResponseEntity<Organisateur> creerOrganisateur(@RequestBody Organisateur organisateur) {
        Organisateur nouvelOrganisateur = organisateurService.creerOrganisateur(organisateur);
        return new ResponseEntity<>(nouvelOrganisateur, HttpStatus.CREATED);
    }

    @GetMapping("/{organisateurId}")
    public ResponseEntity<Organisateur> obtenirOrganisateur(@PathVariable Long organisateurId) {
        Organisateur organisateur = organisateurService.obtenirOrganisateur(organisateurId);
        return new ResponseEntity<>(organisateur, HttpStatus.OK);
    }

    @GetMapping("/{organisateurId}/evenements")
    public ResponseEntity<List<Evenement>> obtenirEvenementsOrganises(@PathVariable Long organisateurId) {
        List<Evenement> evenements = organisateurService.obtenirEvenementsOrganises(organisateurId);
        return new ResponseEntity<>(evenements, HttpStatus.OK);
    }
}
