package com.bepay.application.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public enum DeliveryTypeEnum {
    DELIVERY_TYPE_PUBLISH(0,"publish"),
    DELIVERY_TYPE_SCHEDULE(1,"schedule")
    ;
    private int key;
    private String value;
    DeliveryTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }
    private static final Map<Integer, DeliveryTypeEnum> mKeyValues = new HashMap<Integer, DeliveryTypeEnum>();
    private static final Map<String, DeliveryTypeEnum> mValues = new HashMap<String, DeliveryTypeEnum>();
    static {
        for (DeliveryTypeEnum dt : DeliveryTypeEnum.values()) {
            mValues.put(dt.getValue(), dt);
            mKeyValues.put(dt.getKey(), dt);
        }
    }

    public static DeliveryTypeEnum get(int value) {
        return mKeyValues.get(value);
    }

    public static DeliveryTypeEnum get(String key) {
        return mValues.get(key);
    }



}
