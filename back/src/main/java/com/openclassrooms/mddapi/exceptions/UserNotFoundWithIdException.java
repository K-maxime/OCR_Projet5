package com.openclassrooms.mddapi.exceptions;

/**
 * Exception levee quand aucun utilisateur ne correspond au login fourni.
 */
public class UserNotFoundWithIdException extends RuntimeException {

    public UserNotFoundWithIdException(Long id) {
        super("Aucun utilisateur trouve avec l'Id " + id );
    }
}
