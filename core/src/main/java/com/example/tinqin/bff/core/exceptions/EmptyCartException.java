package com.example.tinqin.bff.core.exceptions;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(){
        super("Cart is empty");
    }
}
