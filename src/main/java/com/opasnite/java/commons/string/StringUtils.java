package com.opasnite.java.commons.string;

public class StringUtils {

    public static Boolean isNullOrEmptyString(String string) {
        boolean result = false;
        if (string == null || "".equals(string.trim())) result = true;
        return result;
    }

    public static String getOrDefault(String target, String defaultVal) {
        return StringUtils.isNullOrEmptyString(target) ? defaultVal : target;
    }
}