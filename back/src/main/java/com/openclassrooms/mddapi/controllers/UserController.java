package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UpdateUserRequestDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur des endpoints utilisateur.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Met a jour le profil de l'utilisateur connecte.
     *
     * @param request les nouvelles informations du profil
     * @return l'utilisateur mis a jour
     */
    @PutMapping("/me")
    public ResponseEntity<MessageResponse> updateUser(@Valid @RequestBody UpdateUserRequestDto request) {
        return ResponseEntity.ok(userService.updateProfile(request.getEmail(), request.getUsername(), request.getPassword()));
    }
}
