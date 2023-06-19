package com.bepay.application.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeMessageEnum {
    TYPE1(1,"Notification"),
    TYPE2(2,"Promation");

    private final int status;
    private final String value;

    private TypeMessageEnum(int status, String value) {
        this.status = status;
        this.value = value;
    }

    private static final Map<Integer, TypeMessageEnum> mStatus = new HashMap<Integer,  TypeMessageEnum>();

    static {
        for(TypeMessageEnum t: TypeMessageEnum.values()){
            mStatus.put(t.status(), t);
        }
    }

    private Integer status() {
        return this.status;
    }

    public int getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }

    public static TypeMessageEnum getStatus(int status) {
        return mStatus.get(status);
    }
}
