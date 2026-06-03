package com.openclassrooms.mddapi.exceptions;

public class SubscriptionAlreadyExistsException extends RuntimeException {
    public SubscriptionAlreadyExistsException() {
        super("Vous êtes déjà abonné à ce thème.");
    }

}
