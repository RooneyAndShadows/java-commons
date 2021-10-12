package com.rands.java.commons.email;

import com.rands.java.commons.email.exception.EmailMessagePreparationException;
import com.rands.java.commons.email.exception.ServiceInitializationErrorException;

public interface IEmailSender {
    EmailDeliveryReport sendEmail(EmailMessage message) throws ServiceInitializationErrorException, EmailMessagePreparationException;
}
