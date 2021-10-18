package com.github.rooneyandshadows.commons.mapping.exception;

public class MappingException extends Exception {

    public MappingException() {
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
