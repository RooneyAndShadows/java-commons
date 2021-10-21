package com.github.rooneyandshadows.java.commons.email;

import java.util.ArrayList;
import java.util.List;

public class EmailDeliveryReport {
    public final boolean IOSuccessful;
    public List<String> warnings = new ArrayList<>();
    public List<String> errors = new ArrayList<>();

    public EmailDeliveryReport(boolean ioSuccessful) {
        IOSuccessful = ioSuccessful;
    }
}
