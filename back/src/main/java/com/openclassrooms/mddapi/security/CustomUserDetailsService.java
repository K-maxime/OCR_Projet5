package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Service Spring Security pour charger les détails utilisateur.
 * Implémente UserDetailsService pour l'authentification.
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Charge les détails utilisateur par nom d'utilisateur.
     *
     * @param username Nom d'utilisateur ou email
     * @return UserDetails de Spring Security
     * @throws UsernameNotFoundException si l'utilisateur n'existe pas
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Chercher par email d'abord (on peut aussi chercher par username)
        User user = userRepository.findByEmailOrUsername(username, username)
                .orElseThrow(() -> {
                    log.warn("Utilisateur non trouvé: {}", username);
                    return new UsernameNotFoundException("Utilisateur non trouvé: " + username);
                });

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    /**
     * Charge les détails utilisateur par ID.
     *
     * @param userId ID utilisateur
     * @return UserDetails de Spring Security
     * @throws UsernameNotFoundException si l'utilisateur n'existe pas
     */
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Utilisateur non trouvé avec l'ID: {}", userId);
                    return new UsernameNotFoundException("Utilisateur non trouvé avec l'ID: " + userId);
                });

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}