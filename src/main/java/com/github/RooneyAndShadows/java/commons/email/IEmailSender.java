package com.github.rooneyandshadows.java.commons.email;

import com.github.rooneyandshadows.java.commons.email.exception.EmailMessagePreparationException;
import com.github.rooneyandshadows.java.commons.email.exception.ServiceInitializationErrorException;

public interface IEmailSender {
    com.github.rooneyandshadows.java.commons.email.EmailDeliveryReport sendEmail(com.github.rooneyandshadows.java.commons.email.EmailMessage message) throws ServiceInitializationErrorException, EmailMessagePreparationException;
}
