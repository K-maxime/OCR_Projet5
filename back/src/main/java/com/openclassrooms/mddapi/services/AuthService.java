package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.UserAlreadyExistsException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithLoginOrInvalidPasswordException;
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
     * @throws UserNotFoundWithLoginOrInvalidPasswordException si aucun utilisateur ne correspond au login ou si le mot de passe est incorrect
     */
    public User login(String login, String password) {
        User user = userRepository.findByEmailOrUsername(login, login)
                .orElseThrow(() -> new UserNotFoundWithLoginOrInvalidPasswordException());

        if (!user.getPassword().equals(password)) {
            throw new UserNotFoundWithLoginOrInvalidPasswordException();
        }

        return user;
    }

    /**
     * Logout user (simple version sans JWT).
     * En prod avec JWT, tu invaliderais le token côté client.
     */
    public MessageResponse logoutUser() {
        //TODO update with token jwt
        return new MessageResponse("Déconnexion réussie");
    }

    public void register(String username, String email, String password) {

        User user = userRepository.findByEmailOrUsername(email, username)
                .orElse(null);

        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);
    }
}
