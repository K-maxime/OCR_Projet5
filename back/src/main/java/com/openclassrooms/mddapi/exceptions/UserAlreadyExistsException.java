package com.openclassrooms.mddapi.exceptions;

/**
 * Exception levee quand un utilisateur existe deja avec le login fourni.
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {

        super("email ou nom d'utilisateur deja utilisé");
    }
}
