package com.opasnite.java.commons.email;

import com.opasnite.java.commons.email.exception.EmailMessagePreparationException;
import com.opasnite.java.commons.email.exception.ServiceInitializationErrorException;

import java.util.List;

public interface IEmailSender {
    EmailDeliveryReport sendEmail(EmailMessage message) throws ServiceInitializationErrorException, EmailMessagePreparationException;
}
