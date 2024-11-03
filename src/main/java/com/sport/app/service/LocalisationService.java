package com.sport.app.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sport.app.entity.Localisation;
import com.sport.app.repository.LocalisationRepository;

@Service
public class LocalisationService {

    @Autowired
    private LocalisationRepository localisationRepository;

    // Method to get all localisations
    public List<Localisation> getAllLocalisations() {
        return localisationRepository.findAll();
    }

    // Method to get a localisation by ID
    public Optional<Localisation> getLocalisationById(Long id) {
        return localisationRepository.findById(id);
    }

    // Method to create or update a localisation
    public Localisation saveLocalisation(Localisation localisation) {
        return localisationRepository.save(localisation);
    }

    // Method to delete a localisation by ID
    public void deleteLocalisation(Long id) {
        localisationRepository.deleteById(id);
    }
}
