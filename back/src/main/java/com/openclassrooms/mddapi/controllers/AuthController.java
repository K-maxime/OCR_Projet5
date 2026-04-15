package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.LoginRequestDto;
import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur des endpoints d'authentification.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Recupere le profil de l'utilisateur connecte.
     *
     * @return les informations de profil
     */
    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        return ResponseEntity.ok("Endpoint non implémente");
    }

    /**
     * Inscrit un nouvel utilisateur.
     *
     * @param request les donnees d'inscription
     * @return une confirmation d'inscription
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok("Endpoint non implémente");
    }

    /**
     * Authentifie un utilisateur.
     *
     * @param request les identifiants de connexion
     * @return le resultat d'authentification
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok("Endpoint non implémente");
    }

    /**
     * Deconnecte l'utilisateur courant.
     *
     * @return une confirmation de deconnexion
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok("Endpoint non implémente");
    }
}
