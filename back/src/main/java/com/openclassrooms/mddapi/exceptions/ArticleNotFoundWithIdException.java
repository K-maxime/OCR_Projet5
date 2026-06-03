package com.openclassrooms.mddapi.exceptions;

public class ArticleNotFoundWithIdException extends RuntimeException {
    public ArticleNotFoundWithIdException(Long id) {

        super("Aucun article trouve avec l'Id " + id );
    }
}
