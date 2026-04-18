package com.openclassrooms.mddapi.exceptions;

public class UnknowSortException extends RuntimeException {
    public UnknowSortException(String sort) {
        super("Type " + sort +" n'est pas un type de trie connue veuillez utiliser 'desc' ou 'asc'");
    }
}
