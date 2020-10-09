package com.opasnite.java.commons.string;

public class StringUtils {

    public static Boolean isNullOrEmptyString(String string) {
        boolean result = false;
        if (string == null || "".equals(string.trim())) result = true;
        return result;
    }
}