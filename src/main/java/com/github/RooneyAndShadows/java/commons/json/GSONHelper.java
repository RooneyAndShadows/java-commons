package com.github.rooneyandshadows.java.commons.json;

import com.github.rooneyandshadows.java.commons.date.DateUtilsOffsetDate;
import com.github.rooneyandshadows.java.commons.date.DateUtilsZonedDate;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class GSONHelper {
    public static final JsonSerializer<ZonedDateTime> ZONED_DATE_TIME_JSON_SERIALIZER = (json, typeOfT, context) -> {
        if (json == null)
            return null;
        return new JsonPrimitive(DateUtilsZonedDate.getDateString(DateUtilsZonedDate.defaultFormatWithTimeZone, json));
    };

    public static final JsonDeserializer<ZonedDateTime> ZONED_DATE_TIME_JSON_DESERIALIZER = (json, typeOfT, context) -> {
        JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
        try {

            // if provided as String - '2011-12-03T10:15:30+01:00[Europe/Paris]'
            if (jsonPrimitive.isString()) {
                return DateUtilsZonedDate.getDateFromString(DateUtilsZonedDate.defaultFormatWithTimeZone, jsonPrimitive.getAsString());
            }

            // if provided as Long
            if (jsonPrimitive.isNumber()) {
                return DateUtilsZonedDate.getDateFromLong(jsonPrimitive.getAsLong(), ZoneId.of("UTC"));
            }

        } catch (RuntimeException e) {
            throw new JsonParseException("Unable to parse ZonedDateTime", e);
        }
        throw new JsonParseException("Unable to parse ZonedDateTime");
    };

    public static final JsonSerializer<OffsetDateTime> THREE_TEN_OFFSET_DATE_TIME_JSON_SERIALIZER = (json, typeOfT, context) -> {
        if (json == null)
            return null;
        return new JsonPrimitive(DateUtilsOffsetDate.getDateString(DateUtilsOffsetDate.defaultFormatWithTimeZone, json));
    };

    public static final JsonDeserializer<OffsetDateTime> OFFSET_DATE_TIME_JSON_DESERIALIZER = (json, typeOfT, context) -> {
        JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
        try {

            // if provided as String - '2011-12-03T10:15:30+01:00[Europe/Paris]'
            if (jsonPrimitive.isString()) {
                OffsetDateTime dateTime = DateUtilsOffsetDate.getDateFromString(DateUtilsOffsetDate.defaultFormatWithTimeZone, jsonPrimitive.getAsString());
                return DateUtilsOffsetDate.toTimeZone(dateTime, ZoneOffset.of(DateUtilsOffsetDate.getLocalTimeZone()));
            }

            // if provided as Long
            if (jsonPrimitive.isNumber()) {
                OffsetDateTime dateTime = DateUtilsOffsetDate.getDateFromLong(jsonPrimitive.getAsLong(), ZoneOffset.of("+0000"));
                return DateUtilsOffsetDate.toTimeZone(dateTime, ZoneOffset.of(DateUtilsOffsetDate.getLocalTimeZone()));
            }

        } catch (RuntimeException e) {
            throw new JsonParseException("Unable to parse OffsetDateTime", e);
        }
        throw new JsonParseException("Unable to parse OffsetDateTime");
    };
}
