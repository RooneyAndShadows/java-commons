package com.opasnite.java.commons.email.exception;

public class ServiceInitializationErrorException extends Exception {
    public ServiceInitializationErrorException(String message) {
        super(message);
    }

    public ServiceInitializationErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
