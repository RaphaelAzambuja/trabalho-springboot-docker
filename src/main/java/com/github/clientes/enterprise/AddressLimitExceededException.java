package com.github.clientes.enterprise;

public class AddressLimitExceededException extends RuntimeException {
    public AddressLimitExceededException(String message) {
        super(message);
    }
}
