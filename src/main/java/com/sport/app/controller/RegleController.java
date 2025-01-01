package com.sport.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Regle;
import com.sport.app.service.services.RegleService;

@RestController
@RequestMapping("/api/regles")
@CrossOrigin("http://localhost:4200")
public class RegleController {

    private final RegleService regleService;

    @Autowired
    public RegleController(RegleService regleService) {
        this.regleService = regleService;
    }

    @GetMapping
    public List<Regle> getAllRegles() {
        return regleService.getAllRegles();
    }

    @GetMapping("/{id}")
    public Regle getRegleById(@PathVariable Long id) {
        return regleService.getRegleById(id);
    }

    @PostMapping
    public Regle createRegle(@RequestBody Regle regle) {
        return regleService.createRegle(regle);
    }

    @PutMapping("/{id}")
    public Regle updateRegle(@PathVariable Long id, @RequestBody Regle regle) {
        return regleService.updateRegle(id, regle);
    }

    @DeleteMapping("/{id}")
    public void deleteRegle(@PathVariable Long id) {
        regleService.deleteRegle(id);
    }
    @GetMapping("/typeDeSport/{typeDeSportId}")
    public List<Regle> getReglesByTypeDeSportId(@PathVariable Long typeDeSportId) {
        return regleService.getReglesByTypeDeSportId(typeDeSportId);
    }
    @GetMapping("/sans-typeDeSport/{typeDeSportId}")
    public List<Regle> getReglesSansTypeDeSport(@PathVariable Long typeDeSportId) {
        return regleService.getReglesSansTypeDeSport(typeDeSportId);
    }
    @GetMapping("/evenement/{evenementId}")
    public List<Regle> getReglesByEvenementId(@PathVariable Long evenementId) {
        return regleService.getReglesByEvenementId(evenementId);
    }

}