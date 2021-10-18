package com.github.rooneyandshadows.commons.email;

import com.github.rooneyandshadows.commons.email.exception.EmailMessagePreparationException;
import com.github.rooneyandshadows.commons.email.exception.ServiceInitializationErrorException;

public interface IEmailSender {
    EmailDeliveryReport sendEmail(EmailMessage message) throws ServiceInitializationErrorException, EmailMessagePreparationException;
}
