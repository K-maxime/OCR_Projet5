package com.openclassrooms.mddapi.exceptions;

public class SubjectNotFoundWithIdException extends RuntimeException {
    public SubjectNotFoundWithIdException(long id) {

        super("Aucun subject trouve avec l'Id " + id );
    }
}
