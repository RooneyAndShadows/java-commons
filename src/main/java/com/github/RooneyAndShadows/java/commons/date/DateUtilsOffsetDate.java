package com.github.rooneyandshadows.java.commons.date;


import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@SuppressWarnings("unused")
public class DateUtilsOffsetDate {
    public static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String defaultFormatWithTimeZone = "yyyy-MM-dd HH:mm:ssZ";
    public static final String defaultFormatWithoutTime = "yyyy-MM-dd";

    public static String getLocalTimeZone() {
        String offset = new SimpleDateFormat("X").format(new Date());
        offset = offset.substring(0, 3);
        return offset;
    }

    public static OffsetDateTime nowLocal() {
        return now(ZoneOffset.systemDefault());
    }

    public static OffsetDateTime nowUTC() {
        return now(ZoneOffset.UTC);
    }

    public static OffsetDateTime now(ZoneOffset timezone) {
        return OffsetDateTime.now(timezone);
    }

    public static OffsetDateTime now(ZoneId timezone) {
        return OffsetDateTime.now(timezone);
    }

    public static OffsetDateTime toUTC(OffsetDateTime date) {
        return toTimeZone(date, ZoneOffset.UTC);
    }

    public static OffsetDateTime toTimeZone(OffsetDateTime date, ZoneOffset timeZone) {
        if (date == null)
            return null;
        return date.withOffsetSameInstant(timeZone);
    }

    public static Date toDate(OffsetDateTime date) {
        return new Date(date.toInstant().toEpochMilli());
    }

    public static OffsetDateTime fromDate(Date date) {
        return fromDate(date, getLocalTimeZone());
    }

    public static OffsetDateTime fromDate(Date date, String toTimezone) {
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(date.toInstant().toEpochMilli()), ZoneOffset.of(toTimezone));
    }

    public static OffsetDateTime date(int year, int month, int day, int hour, int minute, int second, ZoneOffset timezone) {
        return OffsetDateTime.of(year, month, day, hour, minute, second, 0, timezone);
    }

    public static OffsetDateTime date(int year, int month, int day, int hour, int minute, int second) {
        return date(year, month, day, hour, minute, second, ZoneOffset.of(getLocalTimeZone()));
    }

    public static OffsetDateTime date(int year, int month, int day) {
        return date(year, month, day, 0, 0, 0);
    }

    public static OffsetDateTime date(int year, int month) {
        return date(year, month, 1, 0, 0, 0);
    }

    public static boolean isDateInRange(OffsetDateTime testDate, OffsetDateTime start, OffsetDateTime end) {
        return isDateInRange(testDate, start, end, true);
    }

    public static boolean isDateInRange(OffsetDateTime testDate, OffsetDateTime start, OffsetDateTime end, boolean withTime) {
        if (!withTime) {
            start = setTimeToDate(start, 0, 0, 0);
            end = setTimeToDate(end, 23, 59, 59);
        }
        return !testDate.isBefore(start) && !testDate.isAfter(end);
    }

    public static boolean isDateBefore(OffsetDateTime testDate, OffsetDateTime target) {
        return testDate.compareTo(target) < 0;
    }

    public static boolean isDateBeforeOrEqual(OffsetDateTime testDate, OffsetDateTime target) {
        return testDate.compareTo(target) <= 0;
    }

    public static boolean isDateAfter(OffsetDateTime testDate, OffsetDateTime target) {
        return testDate.compareTo(target) > 0;
    }

    public static boolean isDateAfterOrEqual(OffsetDateTime testDate, OffsetDateTime target) {
        return testDate.compareTo(target) >= 0;
    }

    public static boolean isDateEqual(OffsetDateTime testDate, OffsetDateTime target) {
        return isDateEqual(testDate, target, true);
    }

    public static boolean isDayToday(OffsetDateTime testDate) {
        return isDateEqual(testDate, DateUtilsOffsetDate.now(testDate.getOffset()), false);
    }

    public static boolean isDayYesterday(OffsetDateTime testDate) {
        OffsetDateTime yesterdayDate = DateUtilsOffsetDate.incrementDate(DateUtilsOffsetDate.now(testDate.getOffset()), PeriodTypes.DAY, -1, 0);
        return isDateEqual(testDate, yesterdayDate, false);
    }

    public static boolean isDayTomorrow(OffsetDateTime testDate) {
        OffsetDateTime tomorrowDate = DateUtilsOffsetDate.incrementDate(DateUtilsOffsetDate.now(testDate.getOffset()), PeriodTypes.DAY, 1, 0);
        return isDateEqual(testDate, tomorrowDate, false);
    }

    public static boolean isDayAfterTomorrow(OffsetDateTime testDate) {
        OffsetDateTime dayAfterTomorrowDate = DateUtilsOffsetDate.incrementDate(DateUtilsOffsetDate.now(testDate.getOffset()), PeriodTypes.DAY, 2, 0);
        return isDateEqual(testDate, dayAfterTomorrowDate, false);
    }

    public static boolean isDateEqual(OffsetDateTime testDate, OffsetDateTime target, boolean withTime) {
        if (testDate == null || target == null)
            return testDate == target;
        if (!withTime) {
            testDate = DateUtilsOffsetDate.setTimeToDate(testDate, 0, 0, 0);
            target = DateUtilsOffsetDate.setTimeToDate(target, 0, 0, 0);
        }
        return testDate.compareTo(target) == 0;
    }

    public static OffsetDateTime getFirstDayOfMonthDate(OffsetDateTime date) {
        if (date == null)
            return null;
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static OffsetDateTime getLastDayOfMonthDate(OffsetDateTime date) {
        if (date == null)
            return null;
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static Integer getHourOfDay(OffsetDateTime date) {
        if (date == null)
            return null;
        return date.getHour();
    }

    public static Integer getMinuteOfHour(OffsetDateTime date) {
        if (date == null)
            return null;
        return date.getMinute();
    }

    public static Integer getSecondOfMinute(OffsetDateTime date) {
        if (date == null)
            return null;
        return date.getSecond();
    }

    public static Long getLongRepresentation(OffsetDateTime date) {
        if (date == null)
            return null;
        return date.toEpochSecond();
    }

    public static OffsetDateTime getDateFromLong(Long dateRepresentation) {
        return getDateFromLong(dateRepresentation, ZoneOffset.of(getLocalTimeZone()));
    }

    public static OffsetDateTime getDateFromLong(Long dateRepresentation, ZoneId zoneId) {
        return dateRepresentation == null ? null : OffsetDateTime.ofInstant(Instant.ofEpochSecond(dateRepresentation), zoneId);
    }

    public static OffsetDateTime getDateWithDayOfWeek(OffsetDateTime date, int dayOfWeek) {
        return date.with(DayOfWeek.of(dayOfWeek));
    }

    public static OffsetDateTime getDateWithoutTime(OffsetDateTime date) {
        if (date == null)
            return null;
        return date.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    public static OffsetDateTime getDateFromStringInDefaultFormat(String dateString) {
        return getDateFromStringInDefaultFormat(dateString, true);
    }

    public static OffsetDateTime getDateFromStringInDefaultFormat(String dateString, boolean withTime) {
        return getDateFromString(withTime ? defaultFormat : defaultFormatWithoutTime, dateString);
    }

    public static OffsetDateTime getDateFromString(String format, String dateString) {
        if (dateString == null || dateString.equals(""))
            return null;
        if (format == null || format.equals(""))
            format = defaultFormat;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return OffsetDateTime.parse(dateString, formatter);
    }

    public static String getDateStringInDefaultFormat(OffsetDateTime date) {
        return getDateStringInDefaultFormat(date, true);
    }

    public static String getDateStringInDefaultFormat(OffsetDateTime date, boolean withTime) {
        return getDateStringInDefaultFormat(date, Locale.getDefault(), withTime);
    }

    public static String getDateStringInDefaultFormat(OffsetDateTime date, Locale locale) {
        return getDateStringInDefaultFormat(date, locale, true);
    }

    public static String getDateStringInDefaultFormat(OffsetDateTime date, Locale locale, boolean withTime) {
        return getDateString(withTime ? defaultFormat : defaultFormatWithoutTime, date, locale);
    }

    public static String getDateString(String format, OffsetDateTime date) {
        return getDateString(format, date, Locale.getDefault());
    }

    public static String getDateString(String format, OffsetDateTime date, Locale locale) {
        if (date == null)
            return null;
        if (format == null || format.equals(""))
            format = defaultFormat;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        return date.format(formatter);
    }

    public static int getDaysBetweenDates(OffsetDateTime date1, OffsetDateTime date2) {
        return getPeriodsInInterval(getDateWithoutTime(date1), getDateWithoutTime(date2), PeriodTypes.DAY);
    }

    public static List<OffsetDateTime> getAllMonthsForYear(int year) {
        ArrayList<OffsetDateTime> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            OffsetDateTime newDate = DateUtilsOffsetDate.date(year, i, 1);
            months.add(newDate);
        }
        return months;
    }

    public static Integer extractDayOfMonthFromDate(OffsetDateTime date) {
        if (date == null)
            return null;
        return date.getDayOfMonth();
    }

    public static int extractMonthOfYearFromDate(OffsetDateTime date) {
        if (date == null)
            return 0;
        return date.getMonthValue();
    }

    public static int extractYearFromDate(OffsetDateTime date) {
        if (date == null)
            return 0;
        return date.getYear();
    }

    public static Integer getPeriodsInInterval(OffsetDateTime startDate, OffsetDateTime endDate, PeriodTypes type) {
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

    public static OffsetDateTime setTimeToDate(OffsetDateTime date, int hour, int minutes, int seconds) {
        if (date == null)
            return null;
        return date.withHour(hour)
                .withMinute(minutes)
                .withSecond(seconds)
                .withNano(0);
    }

    public static OffsetDateTime setYearToDate(OffsetDateTime date, int year) {
        if (date == null)
            return null;
        return date.withYear(year);
    }

    public static OffsetDateTime setMonthToDate(OffsetDateTime date, int month) {
        if (date == null)
            return null;
        if (month < 1)
            month = 1;
        if (month > 12)
            month = 12;
        return date.withMonth(month);
    }

    public static OffsetDateTime incrementDate(OffsetDateTime date, PeriodTypes type, int periods, int missPeriods) {
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

    public static OffsetDateTime addSeconds(OffsetDateTime date, int seconds) {
        if (date == null)
            return null;
        return date.plusSeconds(seconds);
    }

    public static OffsetDateTime addMinutes(OffsetDateTime date, int minutes) {
        if (date == null)
            return null;
        return date.plusMinutes(minutes);
    }

    public static OffsetDateTime addHours(OffsetDateTime date, int hours) {
        if (date == null)
            return null;
        return date.plusHours(hours);
    }

    private static OffsetDateTime addDays(OffsetDateTime date, int days, int missDays) {
        if (missDays < 0)
            missDays = 0;
        missDays = missDays + 1;
        return date.plusDays((long) days * missDays);
    }

    private static OffsetDateTime addWeeks(OffsetDateTime date, int weeks, int missWeeks) {
        if (missWeeks < 0)
            missWeeks = 0;
        missWeeks = missWeeks + 1;
        int daysToAdd = 7 * weeks * missWeeks;
        return date.plusDays(daysToAdd);
    }

    private static OffsetDateTime addMonths(OffsetDateTime date, int months, int missMonths) {
        if (missMonths < 0)
            missMonths = 0;
        missMonths = missMonths + 1;
        return date.plusMonths((long) months * missMonths);
    }

    private static OffsetDateTime addYears(OffsetDateTime date, int years, int missYears) {
        if (missYears < 0)
            missYears = 0;
        missYears = missYears + 1;
        return date.plusYears((long) years * missYears);
    }

    public static OffsetDateTime getDateObjectByYearAndMonth(int year, int month) {
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