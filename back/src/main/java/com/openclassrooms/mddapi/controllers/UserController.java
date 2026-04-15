package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UpdateUserRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur des endpoints utilisateur.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * Met a jour le profil de l'utilisateur connecte.
     *
     * @param request les nouvelles informations du profil
     * @return l'utilisateur mis a jour
     */
    @PutMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequestDto request) {
        return ResponseEntity.ok("Endpoint non implémente");
    }
}
