package com.github.RooneyAndShadows.commons.email;

import com.github.RooneyAndShadows.commons.email.exception.EmailMessagePreparationException;
import com.github.RooneyAndShadows.commons.email.exception.ServiceInitializationErrorException;

public interface IEmailSender {
    EmailDeliveryReport sendEmail(EmailMessage message) throws ServiceInitializationErrorException, EmailMessagePreparationException;
}
