package com.sport.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.Classement;
import com.sport.app.entity.ClassementEquipeSimple;
import com.sport.app.entity.ClassementSimple;
import com.sport.app.service.ClassementService;

@RestController
@RequestMapping("/api/classements")
public class ClassementController {

    @Autowired
    private ClassementService classementService;
    // AFFICHAGE GLOBALEEEEE DES CLASSEMENTS SELON LE TYPE DE SPORT 
    @GetMapping("/typeSportId/{typeSportId}/classementGlobal")
    public List<Object> getGlobalClassementsByTypeSportId(@PathVariable Long typeSportId) {
        return classementService.getClassementGlobalParTypeDeSportId(typeSportId);
    }
    // CLASSEMENET SELON LES EVENEMENTS
    @GetMapping("/evenement/{evenementId}")
    public List<Classement> getClassementsByEvenement(@PathVariable Long evenementId) {
        return classementService.findByEvenement(evenementId);
    }

    // Add classement
    @PostMapping("/add")
    public Classement addClassement(@RequestBody Classement classement) {
        return classementService.saveClassement(classement);
    }
    
    @GetMapping("/evenement/sorted/{evenementId}")
    public List<Classement> getSortedClassementsByEvenement(@PathVariable Long evenementId) {
        return classementService.findClassementsByEvenementSorted(evenementId);
    }
    
    // HADA HOWA LI SIMPLIFIé HOWA LI KHDAAAAAM
    @GetMapping("/evenement/{evenementId}/simple")
    public List<ClassementSimple> getSimpleClassementByEvenement(@PathVariable Long evenementId) {
        return classementService.getSimpleClassementByEvenement(evenementId);
    }
    
    @GetMapping("/evenement/sortedByButs/{evenementId}")
    public List<Classement> getSortedByButsClassementsByEvenement(@PathVariable Long evenementId) {
        return classementService.findClassementsByEvenementSortedByButs(evenementId);
    }
    ///// HADA SIMPLIFIé HOWA LI KHDAAAAAAM
    @GetMapping("/evenement/simplifiedTeams/{evenementId}")
    public List<ClassementEquipeSimple> getSimpleTeamClassementsByEvenement(@PathVariable Long evenementId) {
        return classementService.getSimpleClassementEquipeByEvenement(evenementId);
    }
   


}
