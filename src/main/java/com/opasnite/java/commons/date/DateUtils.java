package com.opasnite.java.commons.date;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unused")
public class DateUtils {
    public static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String defaultFormatWithoutTime = "yyyy-MM-dd";

    public static Date now() {
        return new Date();
    }

    public static Date date(int year, int month, int day, int hour, int minute, int second) {
        DateTime dateTime = new DateTime(DateTimeZone.UTC)
                .withYear(year)
                .withMonthOfYear(month)
                .withDayOfMonth(day)
                .withHourOfDay(hour)
                .withMinuteOfHour(minute)
                .withSecondOfMinute(second)
                .withMillisOfSecond(0);
        return convertDateTimeToDate(dateTime);
    }

    public static Date date(int year, int month, int day) {
        return date(year, month, day, 0, 0, 0);
    }

    public static Date date(int year, int month) {
        return date(year, month, 1, 0, 0, 0);
    }

    public static boolean isDateInRange(Date testDate, Date start, Date end) {
        return !testDate.before(start) && !testDate.after(end);
    }

    public static boolean isDateBefore(Date testDate, Date target) {
        return testDate.compareTo(target) < 0;
    }

    public static boolean isDateBeforeOrEqual(Date testDate, Date target) {
        return testDate.compareTo(target) <= 0;
    }

    public static boolean isDateAfter(Date testDate, Date target) {
        return testDate.compareTo(target) > 0;
    }

    public static boolean isDateAfterOrEqual(Date testDate, Date target) {
        return testDate.compareTo(target) >= 0;
    }

    public static boolean isDateEqual(Date testDate, Date target) {
        return isDateEqual(testDate, target, true);
    }

    public static boolean isDateEqual(Date testDate, Date target, boolean withTime) {
        if (testDate == null || target == null)
            return testDate == target;
        if (!withTime) {
            testDate = DateUtils.setTimeToDate(testDate, 0, 0, 0);
            target = DateUtils.setTimeToDate(target, 0, 0, 0);
        }
        return testDate.compareTo(target) == 0;
    }

    public static Date getFirstDayOfMonthDate(Date date) {
        if (date == null)
            return null;
        DateTime dateTime = new DateTime(date).dayOfMonth().withMinimumValue();
        return convertDateTimeToDate(dateTime);
    }

    public static Date getLastDayOfMonthDate(Date date) {
        if (date == null)
            return null;
        DateTime dateTime = new DateTime(date).dayOfMonth().withMaximumValue();
        return convertDateTimeToDate(dateTime);
    }

    public static Integer getHourOfDay(Date date) {
        if (date == null)
            return null;
        DateTime datetime = new DateTime(date);
        return datetime.hourOfDay().get();
    }

    public static Integer getMinuteOfHour(Date date) {
        if (date == null)
            return null;
        DateTime datetime = new DateTime(date);
        return datetime.minuteOfHour().get();
    }

    public static Long getLongRepresentation(Date date) {
        if (date == null)
            return null;
        return date.getTime();
    }

    public static Date getDateFromLong(Long dateRepresentation) {
        return dateRepresentation == null ? null : new Date(dateRepresentation);
    }

    public static Date getDateWithoutTime(Date date) {
        if (date == null)
            return null;
        DateTime dateTime = new DateTime(date);
        LocalDate dateWithoutTime = new LocalDate(dateTime);
        return dateWithoutTime.toDate();
    }

    public static Date getDateFromString(String format, String dateString) {
        if (dateString == null || dateString.equals(""))
            return null;
        if (format == null || format.equals(""))
            format = defaultFormat;
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = formatter.parseDateTime(dateString);
        return convertDateTimeToDate(dateTime);
    }

    public static Date getDateFromStringInDefaultFormat(String dateString, boolean withTime) {
        if (dateString == null || dateString.equals(""))
            return null;
        DateTimeFormatter formatter = DateTimeFormat.forPattern(withTime ? defaultFormat : defaultFormatWithoutTime);
        DateTime dateTime = formatter.parseDateTime(dateString);
        return convertDateTimeToDate(dateTime);
    }

    public static Date getDateFromStringInDefaultFormat(String dateString) {
        return getDateFromStringInDefaultFormat(dateString, true);
    }

    public static String getDateString(String format, Date date) {
        if (date == null)
            return null;
        if (format == null || format.equals(""))
            format = defaultFormat;
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = new DateTime(date);
        return formatter.print(dateTime);
    }

    public static String getDateString(String format, Date date, Locale locale) {
        if (date == null)
            return null;
        if (format == null || format.equals(""))
            format = defaultFormat;
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = new DateTime(date);
        return formatter.withLocale(locale).print(dateTime);
    }

    public static String getDateStringInDefaultFormat(Date date, boolean withTime) {
        if (date == null)
            return null;
        DateTimeFormatter formatter = DateTimeFormat.forPattern(withTime ? defaultFormat : defaultFormatWithoutTime);
        DateTime dateTime = new DateTime(date);
        return formatter.print(dateTime);
    }

    public static String getDateStringInDefaultFormat(Date date, Locale locale, boolean withTime) {
        if (date == null)
            return null;
        DateTimeFormatter formatter = DateTimeFormat.forPattern(withTime ? defaultFormat : defaultFormatWithoutTime);
        DateTime dateTime = new DateTime(date);
        return formatter.withLocale(locale).print(dateTime);
    }

    public static String getDateStringInDefaultFormat(Date date, Locale locale) {
        return getDateStringInDefaultFormat(date, locale, true);
    }

    public static String getDateStringInDefaultFormat(Date date) {
        return getDateStringInDefaultFormat(date, true);
    }


    public static ArrayList<Date> getAllMonthsForYear(int year) {
        ArrayList<Date> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Date newDate = DateUtils.date(year, i, 1);
            months.add(newDate);
        }
        return months;
    }

    public static Integer extractDayOfMonthFromDate(Date date) {
        if (date == null)
            return null;
        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfMonth();
    }

    public static Integer extractMonthOfYearFromDate(Date date) {
        if (date == null)
            return null;
        DateTime dateTime = new DateTime(date);
        return dateTime.getMonthOfYear();
    }

    public static Integer extractYearFromDate(Date date) {
        if (date == null)
            return null;
        DateTime dateTime = new DateTime(date);
        return dateTime.getYear();
    }

    public static Integer getPeriodsInInterval(Date startDate, Date endDate, PeriodTypes type) {
        if (startDate == null || endDate == null)
            return null;
        int periods = 0;
        DateTime start = new DateTime(startDate);
        DateTime end = new DateTime(endDate);
        switch (type) {
            case DAY:
                periods = Days.daysBetween(start, end).getDays();
                break;
            case WEEK:
                periods = Weeks.weeksBetween(start, end).getWeeks();
                break;
            case MONTH:
                periods = Months.monthsBetween(start, end).getMonths();
                break;
            case YEAR:
                periods = Years.yearsBetween(start, end).getYears();
        }

        return periods;
    }

    public static Date setTimeToDate(Date date, int hour, int minutes, int seconds) {
        if (date == null)
            return null;
        DateTime dateTime = new DateTime(date).withHourOfDay(hour).withMinuteOfHour(minutes).withSecondOfMinute(seconds);
        return convertDateTimeToDate(dateTime);
    }

    public static Date setYearToDate(Date date, int year) {
        if (date == null)
            return null;
        DateTime dateTime = new DateTime(date).withYear(year);
        return convertDateTimeToDate(dateTime);
    }

    public static Date setMonthToDate(Date date, int month) {
        if (date == null)
            return null;
        if (month < 1)
            month = 1;
        if (month > 12)
            month = 12;
        DateTime dateTime = new DateTime(date).withMonthOfYear(month);
        return convertDateTimeToDate(dateTime);
    }

    public static Date addHours(Date date, Integer hours) {
        if (date == null)
            return null;
        DateTime dateTime = new DateTime(date);
        dateTime.plusHours(hours);
        return convertDateTimeToDate(dateTime);
    }

    public static Date incrementDate(Date date, PeriodTypes type, int periods, int missPeriods) {
        if (date == null || type == null)
            return null;
        switch (type) {
            case DAY:
                date = addDays(date, periods, missPeriods);
                break;
            case WEEK:
                date = addWeeks(date, periods, missPeriods);
                break;
            case MONTH:
                date = addMonths(date, periods, missPeriods);
                break;
            case YEAR:
                date = addYears(date, periods, missPeriods);
        }

        return date;
    }

    private static Date addDays(Date date, int days, int missDays) {
        DateTime dateTime = new DateTime(date);
        if (missDays < 0)
            missDays = 0;
        if (days != 0)
            dateTime = dateTime.plusDays(days + missDays);
        return convertDateTimeToDate(dateTime);
    }

    private static Date addWeeks(Date date, int weeks, int missWeeks) {
        DateTime dateTime = new DateTime(date);
        if (missWeeks < 0)
            missWeeks = 0;
        missWeeks = missWeeks + 1;
        int daysToAdd = 7 * weeks * missWeeks;
        dateTime = dateTime.plusDays(daysToAdd);
        return convertDateTimeToDate(dateTime);
    }

    private static Date addMonths(Date date, int months, int missMonths) {
        DateTime dateTime = new DateTime(date);
        if (missMonths < 0)
            missMonths = 0;
        dateTime = dateTime.plusMonths(months + missMonths);
        return convertDateTimeToDate(dateTime);
    }

    private static Date addYears(Date date, int years, int missYears) {
        DateTime dateTime = new DateTime(date);
        if (missYears < 0)
            missYears = 0;
        dateTime = dateTime.plusYears(years + missYears);
        return convertDateTimeToDate(dateTime);
    }

    public static Date getDateObjectByYearAndMonth(int year, int month) {
        return new DateTime().withYear(year).withMonthOfYear(month).dayOfMonth().withMinimumValue().withTime(0, 0, 0, 0).toDate();
    }

    private static Date convertDateTimeToDate(DateTime dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(defaultFormat);
            String stringToParse = formatter.print(dateTime);
            SimpleDateFormat outFormat = new SimpleDateFormat(defaultFormat);
            return outFormat.parse(stringToParse);
        } catch (ParseException e) {
            return null;
        }
    }

    public enum PeriodTypes {
        DAY(1),
        WEEK(2),
        MONTH(3),
        YEAR(4);

        private Integer value;
        private static final Map<Integer, PeriodTypes> map = new HashMap<>();

        PeriodTypes(int value) {
            this.value = value;
        }

        static {
            for (PeriodTypes periodType : PeriodTypes.values()) {
                map.put(periodType.value, periodType);
            }
        }

        public static PeriodTypes valueOf(int periodTypeValue) {
            return map.get(periodTypeValue);
        }

        public int getValue() {
            return value;
        }
    }
}