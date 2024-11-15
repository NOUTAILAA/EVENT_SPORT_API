package com.sport.app.controller;

import com.sport.app.entity.Localisation;
import com.sport.app.service.LocalisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/localisations")
@CrossOrigin(origins = "http://localhost:4200")
public class LocalisationController {

    @Autowired
    private LocalisationService localisationService;

    // Endpoint to get all localisations
    @GetMapping
    public ResponseEntity<List<Localisation>> getAllLocalisations() {
        List<Localisation> localisations = localisationService.getAllLocalisations();
        return new ResponseEntity<>(localisations, HttpStatus.OK);
    }

    // Endpoint to get a localisation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Localisation> getLocalisationById(@PathVariable Long id) {
        Optional<Localisation> localisation = localisationService.getLocalisationById(id);
        return localisation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to create a new localisation
    @PostMapping
    public ResponseEntity<Localisation> createLocalisation(@RequestBody Localisation localisation) {
        Localisation newLocalisation = localisationService.saveLocalisation(localisation);
        return new ResponseEntity<>(newLocalisation, HttpStatus.CREATED);
    }

    // Endpoint to update an existing localisation
    @PutMapping("/{id}")
    public ResponseEntity<Localisation> updateLocalisation(@PathVariable Long id, @RequestBody Localisation localisationDetails) {
        Optional<Localisation> localisationOptional = localisationService.getLocalisationById(id);
        if (localisationOptional.isPresent()) {
            Localisation localisation = localisationOptional.get();
            localisation.setAdresse(localisationDetails.getAdresse());
            localisation.setVille(localisationDetails.getVille());
            localisation.setPays(localisationDetails.getPays());
            localisation.setLatitude(localisationDetails.getLatitude());  // Correct
            localisation.setLongitude(localisationDetails.getLongitude()); // Correct
            Localisation updatedLocalisation = localisationService.saveLocalisation(localisation);
            return new ResponseEntity<>(updatedLocalisation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete a localisation
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLocalisation(@PathVariable Long id) {
        localisationService.deleteLocalisation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
