package com.devian.detected.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.util.Date;

public class GsonSerializer {

    private static GsonSerializer mInstance;

    public static GsonSerializer getInstance() {
        if (mInstance == null) {
            mInstance = new GsonSerializer();
        }
        return mInstance;
    }

    private JsonSerializer<Date> serializer = (src, typeOfSrc, context) -> src == null ? null
            : new JsonPrimitive(src.getTime());

    private JsonDeserializer<Date> deserializer = (jSon, typeOfT, context) -> jSon == null ? null : new Date(jSon.getAsLong());

    public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, serializer)
                .registerTypeAdapter(Date.class, deserializer).create();
    }

}
