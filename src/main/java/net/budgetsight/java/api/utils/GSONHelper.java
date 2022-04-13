package net.budgetsight.java.api.utils;

import com.github.rooneyandshadows.java.commons.date.DateUtils2;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GSONHelper {
    public static final JsonSerializer<ZonedDateTime> ZONED_DATE_TIME_JSON_SERIALIZER = (json, typeOfT, context) -> {
        if (json == null)
            return null;
        return new JsonPrimitive(DateUtils2.getDateString(DateUtils2.defaultFormatWithTimeZone, json));
    };

    public static final JsonDeserializer<ZonedDateTime> ZONED_DATE_TIME_JSON_DESERIALIZER = (json, typeOfT, context) -> {
        JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
        try {

            // if provided as String - '2011-12-03T10:15:30+01:00[Europe/Paris]'
            if(jsonPrimitive.isString()){
                return DateUtils2.getDateFromString(DateUtils2.defaultFormatWithTimeZone, jsonPrimitive.getAsString());
            }

            // if provided as Long
            if(jsonPrimitive.isNumber()){
                return DateUtils2.getDateFromLong(jsonPrimitive.getAsLong(), ZoneId.of("UTC"));
            }

        } catch(RuntimeException e){
            throw new JsonParseException("Unable to parse ZonedDateTime", e);
        }
        throw new JsonParseException("Unable to parse ZonedDateTime");
    };
}
