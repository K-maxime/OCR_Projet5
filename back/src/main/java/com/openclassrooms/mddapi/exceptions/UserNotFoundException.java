package com.openclassrooms.mddapi.exceptions;

/**
 * Exception levee quand aucun utilisateur ne correspond au login fourni.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String login) {
        super("Aucun utilisateur trouve avec le login : " + login);
    }
}
