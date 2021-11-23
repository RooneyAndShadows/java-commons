package com.github.rooneyandshadows.java.commons.email;

import java.util.ArrayList;
import java.util.List;

public class EmailMessage {
    public List<com.github.rooneyandshadows.java.commons.email.Address> cc = new ArrayList<>();
    public List<com.github.rooneyandshadows.java.commons.email.Address> bcc = new ArrayList<>();
    public List<com.github.rooneyandshadows.java.commons.email.Address> to = new ArrayList<>();
    public List<com.github.rooneyandshadows.java.commons.email.Address> replyTo = new ArrayList<>();

    public final com.github.rooneyandshadows.java.commons.email.Address from;
    public final String subject;
    public final String message;

    public boolean isHTML = false;

    public EmailMessage(com.github.rooneyandshadows.java.commons.email.Address from, String subject, String message) {
        this.from = from;
        this.subject = subject;
        this.message = message;
    }
}
