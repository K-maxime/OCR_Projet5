package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.LoginRequestDto;
import com.openclassrooms.mddapi.dto.responses.LoginResponseDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.responses.UserResponseDto;
import com.openclassrooms.mddapi.mapper.LoginMapper;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.services.AuthService;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginMapper loginMapper;


    /**
     * Recupere le profil de l'utilisateur connecte.
     *
     * @return les informations de profil
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getAuthenticatedUser() {
        //TODO update with token jwt

        return ResponseEntity.ok().body(this.userMapper.toDto(userService.getProfile(1L)));
    }

    /**
     * Inscrit un nouvel utilisateur.
     *
     * @param request les donnees d'inscription
     * @return une confirmation d'inscription
     */
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody RegisterRequestDto request) {
        authService.register(request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Authentifie un utilisateur.
     *
     * @param request les identifiants de connexion
     * @return le resultat d'authentification
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok().body(this.loginMapper.toDto(authService.login(request.getLogin(), request.getPassword())));
    }

    /**
     * Deconnecte l'utilisateur courant.
     *
     * @return une confirmation de deconnexion
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok("Endpoint non implemente");
    }
}
