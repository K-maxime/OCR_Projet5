package com.openclassrooms.mddapi.exceptions;

/**
 * Exception levee quand le mot de passe fourni est invalide.
 */
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Mot de passe incorrect");
    }
}
