package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur des endpoints de themes.
 */
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    /**
     * Recupere la liste des themes.
     *
     * @return la liste des themes
     */
    @GetMapping
    public ResponseEntity<?> getSubjects() {
        return ResponseEntity.ok("Endpoint non implémente");
    }

    /**
     * Abonne l'utilisateur a un theme.
     *
     * @param id l'identifiant du theme
     * @return une confirmation d'abonnement
     */
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribeToSubject(@PathVariable Long id) {
        return ResponseEntity.ok("Endpoint non implémente");
    }

    /**
     * Desabonne l'utilisateur d'un theme.
     *
     * @param id l'identifiant du theme
     * @return une confirmation de desabonnement
     */
    @DeleteMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribeFromSubject(@PathVariable Long id) {
        return ResponseEntity.ok("Endpoint non implémente");
    }
}
