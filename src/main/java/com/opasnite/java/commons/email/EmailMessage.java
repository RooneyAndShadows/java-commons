package com.opasnite.java.commons.email;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

public class EmailMessage {
    public List<Address> cc = new ArrayList<>();
    public List<Address> bcc = new ArrayList<>();
    public List<Address> to = new ArrayList<>();
    public List<Address> replyTo = new ArrayList<>();

    public final Address from;
    public final String subject;
    public final String message;

    public boolean isHTML = false;

    public EmailMessage(Address from, String subject, String message) {
        this.from = from;
        this.subject = subject;
        this.message = message;
    }
}
