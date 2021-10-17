package com.github.RooneyAndShadows.commons.numeric;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberUtils {
    private static final String DEFAULT_FORMAT = "###,##0.00";

    public static String getDoubleString(Double number, String format, DecimalFormatSymbols symbols) {
        DecimalFormat groupeddecimalFormat = new DecimalFormat(format, symbols);
        return groupeddecimalFormat.format(number);
    }

    public static String getShortNameString(double number) {
        return getShortNameString(number, ',', '.');
    }

    public static String getShortNameString(double number, char groupingSeparator, char decimalSeparator) {
        if (number >= 1000000000) {
            return getDoubleString(number / 1000000000.0, groupingSeparator, decimalSeparator).concat("B");
        }

        if (number >= 1000000) {
            return getDoubleString(number / 1000000.0, groupingSeparator, decimalSeparator).concat("M");
        }

        if (number >= 1000) {
            return getDoubleString(number / 1000.0, groupingSeparator, decimalSeparator).concat("K");
        }
        return getDoubleString(number, groupingSeparator, decimalSeparator);
    }

    public static String getDoubleString(Double number, String format) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat groupeddecimalFormat = new DecimalFormat(format, symbols);
        return groupeddecimalFormat.format(number);
    }

    public static String getDoubleString(Double number) {
        return getDoubleString(number, ',', '.');
    }

    public static String getDoubleString(Double number, char groupingSeparator, char decimalSeparator) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(groupingSeparator);
        symbols.setDecimalSeparator(decimalSeparator);
        DecimalFormat groupeddecimalFormat = new DecimalFormat(DEFAULT_FORMAT, symbols);
        return groupeddecimalFormat.format(number);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}