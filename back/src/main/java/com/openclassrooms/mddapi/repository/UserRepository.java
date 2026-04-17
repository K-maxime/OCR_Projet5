package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par email ou nom d'utilisateur.
     *
     * @param email l'email a rechercher
     * @param username le nom d'utilisateur a rechercher
     * @return l'utilisateur correspondant si trouve
     */
    Optional<User> findByEmailOrUsername(String email, String username);
}
