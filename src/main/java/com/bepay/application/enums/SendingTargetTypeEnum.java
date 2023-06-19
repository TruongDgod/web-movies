package com.bepay.application.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public enum SendingTargetTypeEnum {
    SENDING_TYPE_USER_LIST(1,"user list"),
    SENDING_TYPE_USER_SEGMENT(2,"User segment")
    ;
    private int key;
    private String value;
    SendingTargetTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    private static final Map<Integer, SendingTargetTypeEnum> mKeyValues = new HashMap<Integer, SendingTargetTypeEnum>();
    private static final Map<String, SendingTargetTypeEnum> mValues = new HashMap<String, SendingTargetTypeEnum>();
    static {
        for (SendingTargetTypeEnum st : SendingTargetTypeEnum.values()) {
            mValues.put(st.getValue(), st);
            mKeyValues.put(st.getKey(), st);
        }
    }

    public static SendingTargetTypeEnum get(int value) {
        return mKeyValues.get(value);
    }

    public static SendingTargetTypeEnum get(String key) {
        return mValues.get(key);
    }
}
