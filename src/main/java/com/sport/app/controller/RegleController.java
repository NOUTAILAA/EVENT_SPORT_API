package com.sport.app.controller;

import com.sport.app.entity.Regle;
import com.sport.app.service.services.RegleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
