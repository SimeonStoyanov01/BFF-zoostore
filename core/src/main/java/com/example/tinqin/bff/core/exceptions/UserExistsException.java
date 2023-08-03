package com.example.tinqin.bff.core.exceptions;

public class UserExistsException extends RuntimeException{
    public UserExistsException() {
        super("User already exists");
    }
}
