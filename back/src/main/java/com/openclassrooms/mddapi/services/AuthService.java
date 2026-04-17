package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.LoginResponseDto;
import com.openclassrooms.mddapi.exceptions.InvalidPasswordException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service de gestion de l'authentification.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authentifie un utilisateur a partir de son email ou de son nom d'utilisateur.
     *
     * @param login l'email ou le nom d'utilisateur
     * @param password le mot de passe en clair
     * @return les informations de connexion si l'authentification reussit
     * @throws UserNotFoundException si aucun utilisateur ne correspond au login
     * @throws InvalidPasswordException si le mot de passe est incorrect
     */
    public LoginResponseDto login(String login, String password) {
        User user = userRepository.findByEmailOrUsername(login, login)
                .orElseThrow(() -> new UserNotFoundException(login));

        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }

        return new LoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                "Login successful"
        );
    }
}
