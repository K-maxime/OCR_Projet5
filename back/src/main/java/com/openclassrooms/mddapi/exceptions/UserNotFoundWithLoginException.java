package com.openclassrooms.mddapi.exceptions;

/**
 * Exception levee quand aucun utilisateur ne correspond au login fourni.
 */
public class UserNotFoundWithLoginException extends RuntimeException {

    public UserNotFoundWithLoginException(String login) {
        super("Aucun utilisateur trouve avec le login : " + login);
    }
}
