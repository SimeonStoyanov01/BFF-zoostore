package com.example.tinqin.bff.core.exceptions;

public class NoSuchOrderExistsException extends RuntimeException{
    public NoSuchOrderExistsException() {
        super("No such order is found");
    }
}
