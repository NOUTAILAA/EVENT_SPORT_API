package com.sport.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.TypeDeSport;
import com.sport.app.service.TypeDeSportService;

@RestController
@RequestMapping("/typesport")
@CrossOrigin(origins = "http://localhost:4200")
public class TypeDeSportController {

    @Autowired
    private TypeDeSportService typeDeSportService;

    @PostMapping("/creer")
    public ResponseEntity<TypeDeSport> creerTypeDeSport(@RequestBody TypeDeSport typeDeSport) {
        TypeDeSport nouveauType = typeDeSportService.creerTypeDeSport(typeDeSport);
        return new ResponseEntity<>(nouveauType, HttpStatus.CREATED);
    }

    @GetMapping("/{typeDeSportId}")
    public ResponseEntity<TypeDeSport> obtenirTypeDeSport(@PathVariable Long typeDeSportId) {
        TypeDeSport typeDeSport = typeDeSportService.obtenirTypeDeSport(typeDeSportId);
        return new ResponseEntity<>(typeDeSport, HttpStatus.OK);
    }

    @GetMapping("/liste")
    public ResponseEntity<List<TypeDeSport>> obtenirTousLesTypesDeSport() {
        List<TypeDeSport> typesDeSport = typeDeSportService.obtenirTousLesTypesDeSport();
        return new ResponseEntity<>(typesDeSport, HttpStatus.OK);
    }
 // Ajoutez la méthode pour mettre à jour un type de sport
    @PutMapping("/mettreajour/{id}")
    public ResponseEntity<TypeDeSport> mettreAJourTypeDeSport(@PathVariable Long id, @RequestBody TypeDeSport typeDeSport) {
        TypeDeSport typeMisAJour = typeDeSportService.mettreAJourTypeDeSport(id, typeDeSport);
        return new ResponseEntity<>(typeMisAJour, HttpStatus.OK);
    }
}
