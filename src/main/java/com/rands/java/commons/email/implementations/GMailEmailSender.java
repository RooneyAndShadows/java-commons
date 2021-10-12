package com.rands.java.commons.email.implementations;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.rands.java.commons.email.Address;
import com.rands.java.commons.email.EmailDeliveryReport;
import com.rands.java.commons.email.EmailMessage;
import com.rands.java.commons.email.IEmailSender;
import com.rands.java.commons.email.exception.EmailMessagePreparationException;
import com.rands.java.commons.email.exception.ServiceInitializationErrorException;
import org.apache.commons.codec.binary.Base64;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class GMailEmailSender implements IEmailSender {
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final String appName;
    private final String credentialsFilePath;
    private final String tokensDirectoryPath;
    private final String userId;

    private Gmail service;


    public GMailEmailSender(String appName, String credentialsFilePath, String tokensDirectoryPath, String userId) {
        this.appName = appName;
        this.credentialsFilePath = credentialsFilePath;
        this.tokensDirectoryPath = tokensDirectoryPath;
        this.userId = userId;
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        File credentialsFile = new File(credentialsFilePath);
        if (!credentialsFile.exists())
            throw new FileNotFoundException("Credentials not found: " + credentialsFilePath);
        try (InputStream is = new FileInputStream(this.credentialsFilePath);
             InputStreamReader isr = new InputStreamReader(is)) {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, isr);
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensDirectoryPath)))
                    .setAccessType("offline")
                    .build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize(userId);
        }
    }

    public void initialize() throws ServiceInitializationErrorException {
        if (this.service != null)
            return;

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(appName)
                    .build();
        } catch (GeneralSecurityException | IOException exception) {
            throw new ServiceInitializationErrorException("Failed to initialized gmail service!", exception);
        }
    }

    private InternetAddress[] convertAddressListToInternetAddress(List<Address> addresses) throws UnsupportedEncodingException {
        InternetAddress[] internetAddresses = new InternetAddress[addresses.size()];
        int i = -1;
        for (Address address : addresses) {
            i++;
            internetAddresses[i] = convertAddressToInternetAddress(address);
        }
        return internetAddresses;
    }

    private InternetAddress convertAddressToInternetAddress(Address address) throws UnsupportedEncodingException {
        return new InternetAddress(address.emailAddress, address.name);
    }

    private MimeMessage createEmail(EmailMessage message) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

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

    private com.google.api.services.gmail.model.Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        com.google.api.services.gmail.model.Message message = new com.google.api.services.gmail.model.Message();
        message.setRaw(encodedEmail);
        return message;
    }


    @Override
    public EmailDeliveryReport sendEmail(EmailMessage message) throws ServiceInitializationErrorException, EmailMessagePreparationException {
        initialize();

        MimeMessage emailContent;
        com.google.api.services.gmail.model.Message messageConverted;
        try {
            emailContent = createEmail(message);
            messageConverted = createMessageWithEmail(emailContent);
        } catch (MessagingException | IOException e) {
            throw new EmailMessagePreparationException("Failed to create MIMEMessage!", e);
        }
        try {
            service.users().messages().send(userId, messageConverted).execute();
        } catch (IOException e) {
            EmailDeliveryReport report = new EmailDeliveryReport(false);
            report.errors.add(e.getMessage());
            return report;
        }
        return new EmailDeliveryReport(true);

    }
}
