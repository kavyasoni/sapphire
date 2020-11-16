package com.evig.sapphire.utils;

import com.google.gson.*;
import com.google.gson.annotations.Expose;

import java.io.BufferedReader;
import java.lang.reflect.Type;

/**
 * This a Util class to use Gson efficiently
 * <p>
 * Created by ksoni on 01/05/18.
 */
public class GsonUtils {
    /**
     * Method to covert Object to Json String
     *
     * @param object
     * @return String
     */
    public static String convertToJSON(Object object) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        final Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    /**
     * Method to convert json string to Object List of given Type
     * <p>
     * use in case of list : Type listType = new TypeToken<List<String>>() {}.getType();
     *
     * @param jsonString
     * @param typeOfT
     * @param <T>
     * @return Object
     */
    public static <T> T convertToObject(String jsonString, Type typeOfT) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonString, typeOfT);
    }

    /**
     * Method to convert string buffer to Object List of given Type
     * <p>
     * use in case of list : Type listType = new TypeToken<List<String>>() {}.getType();
     *
     * @param bufferedReader
     * @param typeOfT
     * @param <T>
     * @return Object
     */
    public static <T> T convertToObject(BufferedReader bufferedReader, Type typeOfT) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(bufferedReader, typeOfT);
    }

    /**
     * Method to convert JsonObject to Object of given Class Type
     *
     * @param jsonObject
     * @param classOfT
     * @param <T>
     * @return Object
     */
    public static <T> T convertToObject(JsonObject jsonObject, Class<T> classOfT) {
        return convertToObject(jsonObject.toString(), classOfT);
    }

    /**
     * Method to convert json string to Object of given Class Type
     *
     * @param jsonString
     * @param classOfT
     * @param <T>
     * @return Object
     */
    public static <T> T convertToObject(String jsonString, Class<T> classOfT) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonString, classOfT);
    }

    /**
     * Method to convert string buffer to Object of given Class Type
     *
     * @param bufferedReader
     * @param classOfT
     * @param <T>
     * @return Object
     */
    public static <T> T convertToObject(BufferedReader bufferedReader, Class<T> classOfT) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(bufferedReader, classOfT);
    }

    /**
     * Method to get common GsonBuilder
     *
     * @return GsonBuilder
     */
    public static GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes
                        .getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        });
        gsonBuilder
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(
                            FieldAttributes fieldAttributes) {
                        final Expose expose = fieldAttributes
                                .getAnnotation(Expose.class);
                        return expose != null && !expose.deserialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                });
        return gsonBuilder;
    }
}