package com.bepay.application.enums;

import java.util.HashMap;
import java.util.Map;

public enum LanguageEnum {
    ENGLISH(0, "en", "English"),
    FRENCH(1, "fr", "French"),
    KIRUNDI(2, "ht", "Kirundi"),
    ;

    private final int value;
    private final String key;
    private final String description;

    private static final Map<Integer, LanguageEnum> mValues = new HashMap<Integer, LanguageEnum>();
    private static final Map<String, LanguageEnum> mKeyValues = new HashMap<String, LanguageEnum>();

    static {
        for (LanguageEnum lg : LanguageEnum.values()) {
            mValues.put(lg.value(), lg);
            mKeyValues.put(lg.key(), lg);
        }
    }

    private LanguageEnum(int value, String key, String description) {
        this.value = value;
        this.key = key;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public int value() {
        return this.value;
    }

    public String key() {
        return this.key;
    }

    public String description() {
        return this.description;
    }

    public boolean is(int value) {
        return this.value == value;
    }

    public static LanguageEnum get(int value) {
        return mValues.get(value);
    }

    public static LanguageEnum get(String key) {
        return mKeyValues.get(key);
    }

    public static LanguageEnum defaultLanguage() {
        return ENGLISH;
    }

    public static boolean contains(String key) {
        return mKeyValues.containsKey(key);
    }
}
