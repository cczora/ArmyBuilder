package com.cczora.armybuilder.config.exception;

public class DuplicateUsernameException extends Exception {

    public DuplicateUsernameException(String message) {
        super(message);
    }

    public DuplicateUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
