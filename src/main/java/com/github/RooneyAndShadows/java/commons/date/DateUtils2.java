package com.github.RooneyAndShadows.java.commons.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@SuppressWarnings("unused")
public class DateUtils2 {
    public static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String defaultFormatWithTimeZone = "yyyy-MM-dd HH:mm:ssZ";
    public static final String defaultFormatWithoutTime = "yyyy-MM-dd";

    public static ZonedDateTime nowLocal() {
        return now(ZoneId.systemDefault());
    }

    public static ZonedDateTime nowUTC() {
        return now(ZoneId.of("UTC"));
    }

    public static ZonedDateTime now(ZoneId timezone) {
        return ZonedDateTime.now(timezone);
    }

    public static ZonedDateTime toUTC(ZonedDateTime date) {
        return toTimeZone(date, ZoneId.of("UTC"));
    }

    public static ZonedDateTime toTimeZone(ZonedDateTime date, ZoneId timeZone) {
        return date.withZoneSameInstant(timeZone);
    }

    public static ZonedDateTime date(int year, int month, int day, int hour, int minute, int second, ZoneId timezone) {
        return ZonedDateTime.of(year, month, day, hour, minute, second, 0, timezone);
    }

    public static ZonedDateTime date(int year, int month, int day, int hour, int minute, int second) {
        return date(year, month, day, hour, minute, second, ZoneId.systemDefault());
    }

    public static ZonedDateTime date(int year, int month, int day) {
        return date(year, month, day, 0, 0, 0);
    }

    public static ZonedDateTime date(int year, int month) {
        return date(year, month, 1, 0, 0, 0);
    }

    public static boolean isDateInRange(ZonedDateTime testDate, ZonedDateTime start, ZonedDateTime end) {
        return isDateInRange(testDate, start, end, true);
    }

    public static boolean isDateInRange(ZonedDateTime testDate, ZonedDateTime start, ZonedDateTime end, boolean withTime) {
        if (!withTime) {
            start = setTimeToDate(start, 0, 0, 0);
            end = setTimeToDate(end, 23, 59, 59);
        }
        return !testDate.isBefore(start) && !testDate.isAfter(end);
    }

    public static boolean isDateBefore(ZonedDateTime testDate, ZonedDateTime target) {
        return testDate.compareTo(target) < 0;
    }

    public static boolean isDateBeforeOrEqual(ZonedDateTime testDate, ZonedDateTime target) {
        return testDate.compareTo(target) <= 0;
    }

    public static boolean isDateAfter(ZonedDateTime testDate, ZonedDateTime target) {
        return testDate.compareTo(target) > 0;
    }

    public static boolean isDateAfterOrEqual(ZonedDateTime testDate, ZonedDateTime target) {
        return testDate.compareTo(target) >= 0;
    }

    public static boolean isDateEqual(ZonedDateTime testDate, ZonedDateTime target) {
        return isDateEqual(testDate, target, true);
    }

    public static boolean isDayToday(ZonedDateTime testDate) {
        return isDateEqual(testDate, DateUtils2.now(testDate.getZone()), false);
    }

    public static boolean isDayYesterday(ZonedDateTime testDate) {
        ZonedDateTime yesterdayDate = DateUtils2.incrementDate(DateUtils2.now(testDate.getZone()), PeriodTypes.DAY, -1, 0);
        return isDateEqual(testDate, yesterdayDate, false);
    }

    public static boolean isDayTomorrow(ZonedDateTime testDate) {
        ZonedDateTime tomorrowDate = DateUtils2.incrementDate(DateUtils2.now(testDate.getZone()), PeriodTypes.DAY, 1, 0);
        return isDateEqual(testDate, tomorrowDate, false);
    }

    public static boolean isDayAfterTomorrow(ZonedDateTime testDate) {
        ZonedDateTime dayAfterTomorrowDate = DateUtils2.incrementDate(DateUtils2.now(testDate.getZone()), PeriodTypes.DAY, 2, 0);
        return isDateEqual(testDate, dayAfterTomorrowDate, false);
    }

    public static boolean isDateEqual(ZonedDateTime testDate, ZonedDateTime target, boolean withTime) {
        if (testDate == null || target == null)
            return testDate == target;
        if (!withTime) {
            testDate = DateUtils2.setTimeToDate(testDate, 0, 0, 0);
            target = DateUtils2.setTimeToDate(target, 0, 0, 0);
        }
        return testDate.compareTo(target) == 0;
    }

    public static ZonedDateTime getFirstDayOfMonthDate(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static ZonedDateTime getLastDayOfMonthDate(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static Integer getHourOfDay(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.getHour();
    }

    public static Integer getMinuteOfHour(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.getMinute();
    }

    public static Integer getSecondOfMinute(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.getSecond();
    }

    public static Long getLongRepresentation(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.toEpochSecond();
    }

    public static ZonedDateTime getDateFromLong(Long dateRepresentation) {
        return getDateFromLong(dateRepresentation, ZoneId.systemDefault());
    }

    public static ZonedDateTime getDateFromLong(Long dateRepresentation, ZoneId zoneId) {
        return dateRepresentation == null ? null : ZonedDateTime.ofInstant(Instant.ofEpochSecond(dateRepresentation), zoneId);
    }

    public static ZonedDateTime getDateWithDayOfWeek(ZonedDateTime date, int dayOfWeek) {
        return date.with(DayOfWeek.of(dayOfWeek));
    }

    public static ZonedDateTime getDateWithoutTime(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.withHour(0)
                .withMinute(0)
                .withSecond(0);
    }

    public static ZonedDateTime getDateFromStringInDefaultFormat(String dateString) {
        return getDateFromStringInDefaultFormat(dateString, true);
    }

    public static ZonedDateTime getDateFromStringInDefaultFormat(String dateString, boolean withTime) {
        return getDateFromString(withTime ? defaultFormat : defaultFormatWithoutTime, dateString);
    }

    public static ZonedDateTime getDateFromString(String format, String dateString) {
        if (dateString == null || dateString.equals(""))
            return null;
        if (format == null || format.equals(""))
            format = defaultFormat;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return ZonedDateTime.parse(dateString, formatter);
    }

    public static String getDateStringInDefaultFormat(ZonedDateTime date) {
        return getDateStringInDefaultFormat(date, true);
    }

    public static String getDateStringInDefaultFormat(ZonedDateTime date, boolean withTime) {
        return getDateStringInDefaultFormat(date, Locale.getDefault(), withTime);
    }

    public static String getDateStringInDefaultFormat(ZonedDateTime date, Locale locale) {
        return getDateStringInDefaultFormat(date, locale, true);
    }

    public static String getDateStringInDefaultFormat(ZonedDateTime date, Locale locale, boolean withTime) {
        return getDateString(withTime ? defaultFormat : defaultFormatWithoutTime, date, locale);
    }

    public static String getDateString(String format, ZonedDateTime date) {
        return getDateString(format, date, Locale.getDefault());
    }

    public static String getDateString(String format, ZonedDateTime date, Locale locale) {
        if (date == null)
            return null;
        if (format == null || format.equals(""))
            format = defaultFormat;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        return date.format(formatter);
    }

    public static int getDaysBetweenDates(ZonedDateTime date1, ZonedDateTime date2) {
        return getPeriodsInInterval(getDateWithoutTime(date1), getDateWithoutTime(date2), PeriodTypes.DAY);
    }
    public static List<ZonedDateTime> getAllMonthsForYear(int year) {
        ArrayList<ZonedDateTime> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ZonedDateTime newDate = DateUtils2.date(year, i, 1);
            months.add(newDate);
        }
        return months;
    }

    public static Integer extractDayOfMonthFromDate(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.getDayOfMonth();
    }

    public static Integer extractMonthOfYearFromDate(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.getDayOfMonth();
    }

    public static Integer extractYearFromDate(ZonedDateTime date) {
        if (date == null)
            return null;
        return date.getYear();
    }

    public static Integer getPeriodsInInterval(ZonedDateTime startDate, ZonedDateTime endDate, PeriodTypes type) {
        if (startDate == null || endDate == null)
            return null;
        int periods = 0;
        switch (type) {
            case DAY:
                periods = (int) ChronoUnit.DAYS.between(startDate, endDate);
                break;
            case WEEK:
                periods = (int) ChronoUnit.WEEKS.between(startDate, endDate);
                break;
            case MONTH:
                periods = (int) ChronoUnit.MONTHS.between(startDate, endDate);
                break;
            case YEAR:
                periods = (int) ChronoUnit.YEARS.between(startDate, endDate);
        }

        return periods;
    }

    public static ZonedDateTime setTimeToDate(ZonedDateTime date, int hour, int minutes, int seconds) {
        if (date == null)
            return null;
        return date.withHour(hour)
                .withMinute(minutes)
                .withMinute(seconds);
    }

    public static ZonedDateTime setYearToDate(ZonedDateTime date, int year) {
        if (date == null)
            return null;
        return date.withYear(year);
    }

    public static ZonedDateTime setMonthToDate(ZonedDateTime date, int month) {
        if (date == null)
            return null;
        if (month < 1)
            month = 1;
        if (month > 12)
            month = 12;
        return date.withMonth(month);
    }

    public static ZonedDateTime incrementDate(ZonedDateTime date, PeriodTypes type, int periods, int missPeriods) {
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

    public static ZonedDateTime addSeconds(ZonedDateTime date, int seconds) {
        if (date == null)
            return null;
        return date.plusSeconds(seconds);
    }

    public static ZonedDateTime addMinutes(ZonedDateTime date, int minutes) {
        if (date == null)
            return null;
        return date.plusMinutes(minutes);
    }

    public static ZonedDateTime addHours(ZonedDateTime date, int hours) {
        if (date == null)
            return null;
       return date.plusHours(hours);
    }

    private static ZonedDateTime addDays(ZonedDateTime date, int days, int missDays) {
        if (missDays < 0)
            missDays = 0;
        missDays = missDays + 1;
        return date.plusDays((long) days * missDays);
    }

    private static ZonedDateTime addWeeks(ZonedDateTime date, int weeks, int missWeeks) {
        if (missWeeks < 0)
            missWeeks = 0;
        missWeeks = missWeeks + 1;
        int daysToAdd = 7 * weeks * missWeeks;
        return date.plusDays(daysToAdd);
    }

    private static ZonedDateTime addMonths(ZonedDateTime date, int months, int missMonths) {
        if (missMonths < 0)
            missMonths = 0;
        missMonths = missMonths + 1;
        return date.plusMonths((long) months * missMonths);
    }

    private static ZonedDateTime addYears(ZonedDateTime date, int years, int missYears) {
        if (missYears < 0)
            missYears = 0;
        missYears = missYears + 1;
        return date.plusYears((long) years * missYears);
    }

    public static ZonedDateTime getDateObjectByYearAndMonth(int year, int month) {
        return date(year, month);
    }

    public enum PeriodTypes {
        DAY(1),
        WEEK(2),
        MONTH(3),
        YEAR(4);

        private final Integer value;
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