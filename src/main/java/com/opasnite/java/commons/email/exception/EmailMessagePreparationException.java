package com.opasnite.java.commons.email.exception;

public class EmailMessagePreparationException  extends Exception{
    public EmailMessagePreparationException(String message) {
        super(message);
    }

    public EmailMessagePreparationException(String message, Throwable cause) {
        super(message, cause);
    }
}
