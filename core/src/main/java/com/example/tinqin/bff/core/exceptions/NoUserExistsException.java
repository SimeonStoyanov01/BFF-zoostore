package com.example.tinqin.bff.core.exceptions;

public class NoUserExistsException extends RuntimeException{
    public NoUserExistsException() {
        super("No user is found");
    }
}
