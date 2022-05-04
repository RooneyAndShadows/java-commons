package com.github.rooneyandshadows.java.commons.json;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.threeten.bp.ZoneOffset;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GSONHelper {
    public static final JsonSerializer<ZonedDateTime> ZONED_DATE_TIME_JSON_SERIALIZER = (json, typeOfT, context) -> {
        if (json == null)
            return null;
        return new JsonPrimitive(com.github.rooneyandshadows.java.commons.date.DateUtilsZonedDate.getDateString(com.github.rooneyandshadows.java.commons.date.DateUtilsZonedDate.defaultFormatWithTimeZone, json));
    };

    public static final JsonDeserializer<ZonedDateTime> ZONED_DATE_TIME_JSON_DESERIALIZER = (json, typeOfT, context) -> {
        JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
        try {

            // if provided as String - '2011-12-03T10:15:30+01:00[Europe/Paris]'
            if(jsonPrimitive.isString()){
                return com.github.rooneyandshadows.java.commons.date.DateUtilsZonedDate.getDateFromString(com.github.rooneyandshadows.java.commons.date.DateUtilsZonedDate.defaultFormatWithTimeZone, jsonPrimitive.getAsString());
            }

            // if provided as Long
            if(jsonPrimitive.isNumber()){
                return com.github.rooneyandshadows.java.commons.date.DateUtilsZonedDate.getDateFromLong(jsonPrimitive.getAsLong(), ZoneId.of("UTC"));
            }

        } catch(RuntimeException e){
            throw new JsonParseException("Unable to parse ZonedDateTime", e);
        }
        throw new JsonParseException("Unable to parse ZonedDateTime");
    };

    public static final JsonSerializer<org.threeten.bp.OffsetDateTime> THREE_TEN_OFFSET_DATE_TIME_JSON_SERIALIZER = (json, typeOfT, context) -> {
        if (json == null)
            return null;
        return new JsonPrimitive(com.github.rooneyandshadows.java.commons.date.DateUtilsThreeTen.getDateString(com.github.rooneyandshadows.java.commons.date.DateUtilsThreeTen.defaultFormatWithTimeZone, json));
    };

    public static final JsonDeserializer<org.threeten.bp.OffsetDateTime> THREE_TEN_OFFSET_DATE_TIME_JSON_DESERIALIZER = (json, typeOfT, context) -> {
        JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
        try {

            // if provided as String - '2011-12-03T10:15:30+01:00[Europe/Paris]'
            if(jsonPrimitive.isString()){
                return com.github.rooneyandshadows.java.commons.date.DateUtilsThreeTen.getDateFromString(com.github.rooneyandshadows.java.commons.date.DateUtilsThreeTen.defaultFormatWithTimeZone, jsonPrimitive.getAsString());
            }

            // if provided as Long
            if(jsonPrimitive.isNumber()){
                return com.github.rooneyandshadows.java.commons.date.DateUtilsThreeTen.getDateFromLong(jsonPrimitive.getAsLong(), ZoneOffset.of("+0000"));
            }

        } catch(RuntimeException e){
            throw new JsonParseException("Unable to parse OffsetDateTime", e);
        }
        throw new JsonParseException("Unable to parse OffsetDateTime");
    };
}
