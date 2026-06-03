package com.openclassrooms.mddapi.exceptions;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(long id) {

        super("Aucun subscription trouve avec l'Id " + id );
    }
}
