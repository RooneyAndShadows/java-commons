package com.opasnite.java.commons.numeric;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DoubleUtils {
    private static final String DEFAULT_FORMAT = "###,##0.00";

    public static String getDoubleString(Double number, String format, DecimalFormatSymbols symbols) {
        DecimalFormat groupeddecimalFormat = new DecimalFormat(format, symbols);
        return groupeddecimalFormat.format(number);
    }

    public static String getDoubleString(Double number, String format) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat groupeddecimalFormat = new DecimalFormat(format, symbols);
        return groupeddecimalFormat.format(number);
    }

    public static String getDoubleString(Double number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
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