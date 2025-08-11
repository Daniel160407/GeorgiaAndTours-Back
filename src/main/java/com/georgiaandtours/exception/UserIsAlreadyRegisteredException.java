package com.georgiaandtours.exception;

public class UserIsAlreadyRegisteredException extends RuntimeException {
    public UserIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
