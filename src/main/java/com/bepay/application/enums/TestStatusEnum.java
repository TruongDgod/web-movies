package com.bepay.application.enums;

import java.util.HashMap;
import java.util.Map;

public enum TestStatusEnum {
    STATUS1(1, "draft"),
    STATUS3(2,"runing"),
    STATUS4(3,"completed");
    private final int key;
    private final String value;
    private TestStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    private static final Map<Integer, TestStatusEnum> mStatus = new HashMap<Integer,  TestStatusEnum>();

    static {
        for (TestStatusEnum st : TestStatusEnum.values()) {
            mStatus.put(st.key(), st);
        }
    }

    private Integer key() {
        return this.key;
    }
    public static TestStatusEnum getStatus(int key) {
        return mStatus.get(key);
    }


    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
