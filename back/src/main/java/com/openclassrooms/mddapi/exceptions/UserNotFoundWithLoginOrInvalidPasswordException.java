package com.openclassrooms.mddapi.exceptions;

/**
 * Exception levee quand aucun utilisateur ne correspond au login fourni.
 */
public class UserNotFoundWithLoginOrInvalidPasswordException extends RuntimeException {

    public UserNotFoundWithLoginOrInvalidPasswordException() {
        super("Aucun utilisateur trouve avec le login : ");
    }
}
