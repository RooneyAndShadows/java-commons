package com.github.rooneyandshadows.java.commons.email;

import com.github.rooneyandshadows.java.commons.email.exception.EmailMessagePreparationException;
import com.github.rooneyandshadows.java.commons.email.exception.ServiceInitializationErrorException;

public interface IEmailSender {
    EmailDeliveryReport sendEmail(EmailMessage message) throws ServiceInitializationErrorException, EmailMessagePreparationException;
}
