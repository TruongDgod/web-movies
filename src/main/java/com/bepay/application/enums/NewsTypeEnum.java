package com.bepay.application.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public enum NewsTypeEnum {
    NEWS(1,"NEWS", "News"),
    PROMOTION(2,"PROMOTION", "Promotion");
    private final Integer code;
    private final String message;
    private final String desc;

    NewsTypeEnum(Integer code, String message, String desc) {
        this.code = code;
        this.message = message;
        this.desc = desc;
    }

    private static final Map<Integer, NewsTypeEnum> mValues = new HashMap<Integer, NewsTypeEnum>();
    private static final Map<String, NewsTypeEnum> mCodes = new HashMap<String, NewsTypeEnum>();

    static {
        for (NewsTypeEnum s : NewsTypeEnum.values()) {
            mValues.put(s.code, s);
            mCodes.put(s.message, s);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static NewsTypeEnum get(int key) {
        return mValues.get(key);
    }
    public static NewsTypeEnum get(String code) {
        return mCodes.get(code);
    }
}
