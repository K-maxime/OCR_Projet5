package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.UserAlreadyExistsException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithLoginOrInvalidPasswordException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service métier pour la gestion de l'authentification.
 *
 * Gère :
 * - L'enregistrement de nouveaux utilisateurs
 * - La validation des identifiants et mots de passe
 * - La connexion/déconnexion
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    /**
     * Authentifie un utilisateur à partir de son email ou nom d'utilisateur.
     *
     * @param login l'email ou le nom d'utilisateur de l'utilisateur
     * @param password le mot de passe
     * @return le token JWT si l'authentification est réussie
     * @throws UserNotFoundWithLoginOrInvalidPasswordException si aucun utilisateur ne correspond au login
     *         ou si le mot de passe est incorrect
     */
    public String login(String login, String password) {
        User user = userRepository.findByEmailOrUsername(login, login)
                .orElseThrow(() -> new UserNotFoundWithLoginOrInvalidPasswordException());

        if (!user.getPassword().equals(password)) {
            throw new UserNotFoundWithLoginOrInvalidPasswordException();
        }

        // Générer le JWT
        return jwtTokenProvider.generateToken(user.getId(), user.getUsername());

    }

    /**
     * Déconnecte l'utilisateur actuellement authentifié.
     *
     * @return MessageResponse contenant le message de confirmation
     */
    public MessageResponse logoutUser() {
        //TODO update with token jwt
        return new MessageResponse("Déconnexion réussie");
    }

    /**
     * Enregistre un nouvel utilisateur dans le système.
     *
     * Vérifie que l'email et le username ne sont pas déjà utilisés avant de créer le compte.
     *
     * @param username le nom d'utilisateur unique
     * @param email l'adresse email unique
     * @param password le mot de passe
     * @throws UserAlreadyExistsException si l'email ou username existe déjà
     */
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
