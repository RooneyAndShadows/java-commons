package com.github.rooneyandshadows.commons.email;

public class Address {
    public final String emailAddress;
    public String name;

    public Address(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Address(String emailAddress, String name) {
        this.emailAddress = emailAddress;
        this.name = name;
    }
}
