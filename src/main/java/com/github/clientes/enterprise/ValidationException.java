package com.github.clientes.enterprise;

public class ValidationException extends RuntimeException{
    public ValidationException (String message) {
        super(message);
    }
}