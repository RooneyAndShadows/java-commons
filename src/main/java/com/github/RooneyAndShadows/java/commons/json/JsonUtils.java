/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.rooneyandshadows.java.commons.json;

import com.google.gson.*;
import com.github.rooneyandshadows.java.commons.date.DateUtils;

import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author mihail
 */
public class JsonUtils {

    public static Gson buildGson() {
        return new GsonBuilder().setDateFormat(DateUtils.defaultFormat).create();
    }

    public static String toJson(Object target) {
        Gson gson = buildGson();
        return gson.toJson(target);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        Gson gson = buildGson();
        return type.cast(gson.fromJson(json, type));
    }

    public static <T> T fromJson(String json, Type type) {
        Gson gson = buildGson();
        return gson.fromJson(json, type);
    }

    public static boolean compareJsonStrings(String json1, String json2) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement1 = parser.parse(json1);
        JsonElement jsonElement2 = parser.parse(json2);
        boolean result = compareJson(jsonElement1, jsonElement2);
        return result;
    }

    private static boolean compareJson(JsonElement json1, JsonElement json2) {
        boolean isEqual = true;
        // Check whether both jsonElement are not null
        if (json1 != null && json2 != null) {
            // Check whether both jsonElement are objects
            if (json1.isJsonObject() && json2.isJsonObject()) {
                Set<Entry<String, JsonElement>> ens1 = ((JsonObject) json1).entrySet();
                Set<Entry<String, JsonElement>> ens2 = ((JsonObject) json2).entrySet();
                JsonObject json2obj = (JsonObject) json2;
                if (ens1 != null && ens2 != null && (ens2.size() == ens1.size())) {
                    // Iterate JSON Elements with Key values
                    for (Entry<String, JsonElement> en : ens1) {
                        isEqual = isEqual && compareJson(en.getValue(), json2obj.get(en.getKey()));
                    }
                } else {
                    return false;
                }
            } // Check whether both jsonElement are arrays
            else if (json1.isJsonArray() && json2.isJsonArray()) {
                JsonArray jarr1 = json1.getAsJsonArray();
                JsonArray jarr2 = json2.getAsJsonArray();
                if (jarr1.size() != jarr2.size()) {
                    return false;
                } else {
                    int i = 0;
                    // Iterate JSON Array to JSON Elements
                    for (JsonElement je : jarr1) {
                        isEqual = isEqual && compareJson(je, jarr2.get(i));
                        i++;
                    }
                }
            } // Check whether both jsonElement are null
            else if (json1.isJsonNull() && json2.isJsonNull()) {
                return true;
            } // Check whether both jsonElement are primitives
            else if (json1.isJsonPrimitive() && json2.isJsonPrimitive()) {
                return json1.equals(json2);
            } else {
                return false;
            }
        } else {
            return json1 == null && json2 == null;
        }
        return isEqual;
    }
}
