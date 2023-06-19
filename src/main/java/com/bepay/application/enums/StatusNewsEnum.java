package com.bepay.application.enums;

import java.util.HashMap;
import java.util.Map;

public enum StatusNewsEnum {
    STATUS1(1, "Draft"),
    STATUS2(2,"Ready"),
    STATUS3(3,"Processing"),
    STATUS4(4,"Completed");
    private final int key;
    private final String value;

    private StatusNewsEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


    private static final Map<Integer, StatusNewsEnum> mStatus = new HashMap<Integer,  StatusNewsEnum>();

    static {
        for (StatusNewsEnum st : StatusNewsEnum.values()) {
            mStatus.put(st.key(), st);
        }
    }

    private Integer key() {
        return this.key;
    }
    public static StatusNewsEnum getStatus(int key) {
        return mStatus.get(key);
    }


    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
