package com.example.tinqin.bff.core.exceptions;

public class NoSuchItemExistsException extends RuntimeException{
    public NoSuchItemExistsException() {
        super("No such item is found");
    }
}
