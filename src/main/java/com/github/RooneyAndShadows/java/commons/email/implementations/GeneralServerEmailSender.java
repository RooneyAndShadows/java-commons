package com.github.RooneyAndShadows.java.commons.email.implementations;

import com.github.rooneyandshadows.java.commons.email.EmailDeliveryReport;
import com.github.rooneyandshadows.java.commons.email.EmailMessage;
import com.github.rooneyandshadows.java.commons.email.exception.EmailMessagePreparationException;
import com.github.rooneyandshadows.java.commons.email.exception.ServiceInitializationErrorException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

public class GeneralServerEmailSender implements com.github.rooneyandshadows.java.commons.email.IEmailSender {
    private final String mailHost;
    private Integer mailPort = 25;
    private Boolean useSSL = false;
    private Boolean acceptSelfSigned = false;
    private String user = null;
    private String password = null;

    public GeneralServerEmailSender(String mailHost) {
        this.mailHost = mailHost;
    }

    private MimeMessage createEmail(EmailMessage message) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", mailHost);
        props.setProperty("mail.smtp.port", String.valueOf(mailPort));
        if (useSSL) {
            if (mailPort == 587)
                props.setProperty("mail.smtp.starttls.enable", String.valueOf(true));
            else {
                props.setProperty("mail.smtp.ssl.enable", String.valueOf(true));
                if (acceptSelfSigned)
                    props.setProperty("mail.smtp.ssl.trust", mailHost);
            }
        }
        if (user != null) {
            props.setProperty("mail.smtp.auth", String.valueOf(true));
        }
        Authenticator auth = user != null
                ? new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                }
                : null;
        Session session = Session.getDefaultInstance(props, auth);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(convertAddressToInternetAddress(message.from));
        email.addRecipients(javax.mail.Message.RecipientType.TO, convertAddressListToInternetAddress(message.to));
        email.addRecipients(Message.RecipientType.CC, convertAddressListToInternetAddress(message.cc));
        email.addRecipients(Message.RecipientType.BCC, convertAddressListToInternetAddress(message.bcc));
        email.setReplyTo(convertAddressListToInternetAddress(message.replyTo));
        email.setSubject(message.subject);
        if (message.isHTML)
            email.setContent(message.message, "text/html; charset=utf-8");
        else
            email.setText(message.message);
        return email;
    }

    private InternetAddress[] convertAddressListToInternetAddress(List<com.github.rooneyandshadows.java.commons.email.Address> addresses) throws UnsupportedEncodingException {
        InternetAddress[] internetAddresses = new InternetAddress[addresses.size()];
        int i = -1;
        for (com.github.rooneyandshadows.java.commons.email.Address address : addresses) {
            i++;
            internetAddresses[i] = convertAddressToInternetAddress(address);
        }
        return internetAddresses;
    }

    private InternetAddress convertAddressToInternetAddress(com.github.rooneyandshadows.java.commons.email.Address address) throws UnsupportedEncodingException {
        return new InternetAddress(address.emailAddress, address.name);
    }

    @Override
    public EmailDeliveryReport sendEmail(EmailMessage message) throws ServiceInitializationErrorException, EmailMessagePreparationException {
        MimeMessage emailContent;
        try {
            emailContent = createEmail(message);
        } catch (MessagingException | IOException e) {
            throw new EmailMessagePreparationException("Failed to create MIMEMessage!", e);
        }
        try {
            Transport.send(emailContent);
        } catch (MessagingException e) {
            EmailDeliveryReport report = new EmailDeliveryReport(false);
            report.errors.add(e.getMessage());
            return report;
        }
        return new EmailDeliveryReport(true);
    }

    public void setMailPort(Integer mailPort) {
        this.mailPort = mailPort;
    }

    public void setUseSSL(Boolean useSSL) {
        this.useSSL = useSSL;
    }

    public void setAcceptSelfSigned(Boolean acceptSelfSigned) {
        this.acceptSelfSigned = acceptSelfSigned;
    }

    public void setAuth(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
