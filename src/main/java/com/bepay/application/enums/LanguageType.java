package com.bepay.application.enums;

import java.util.HashMap;
import java.util.Map;

public enum LanguageType {
    ENGLISH("en", "english"),
    FRENCH("fr", "french"),
    BURUNDI("ht", "burundi");

    private final String code;
    private final String message;

    private static final Map<String, LanguageType> mValues = new HashMap<String, LanguageType>();

    static {
        for (LanguageType s : LanguageType.values()) {
            mValues.put(s.code, s);
        }
    }

    LanguageType(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static Map<String, LanguageType> getMapStatus() {
        return mValues;
    }
}
