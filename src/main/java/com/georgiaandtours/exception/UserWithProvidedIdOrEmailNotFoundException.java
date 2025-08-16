package com.georgiaandtours.exception;

public class UserWithProvidedIdOrEmailNotFoundException extends RuntimeException {
    public UserWithProvidedIdOrEmailNotFoundException(String message) {
        super(message);
    }
}
