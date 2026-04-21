package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service métier pour la gestion du profil de l'utilisateur
 *
 * Gère :
 * - La mise à jour des informations utilisateur
 * - La récupération des informations utilisateur
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    /**
     * Récupère les informations utilisateur.
     *
     * @return User contenant les informations utilisateur
     */
    public User getProfile(){
        //TODO update with jwt token
        return userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundWithIdException(1L));
    }

    /**
     * Récupère les informations utilisateur.
     *
     * @param email nouvel email à enregistrer
     * @param username nouvel nom d'utilisateur à enregistrer
     * @param password nouvel mot de passe à enregistrer
     * @return MessageResponse un message de confirmation
     */
    public MessageResponse updateProfile(String email, String username, String password){

        //TODO update with jwt token
        User user = getProfile();

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        userRepository.save(user);

        return new MessageResponse("User updated successfully!");

    }




}
