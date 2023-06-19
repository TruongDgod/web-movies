package com.bepay.application.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
public enum StatusGroupDataEnum {
    ON(0, "ON", Arrays.asList(0),"On"),
    OFF(1,"OFF", Arrays.asList(1), "Off"),
    ALL(2,"ALL", Arrays.asList(0,1),"All");
    private final int key;
    private final String value;

    private final List<Integer> list;

    private final String desc;

    StatusGroupDataEnum(int key, String value, List<Integer> list, String desc) {
        this.key = key;
        this.value = value;
        this.list = list;
        this.desc = desc;
    }

    private static final Map<Integer, StatusGroupDataEnum> mStatus = new HashMap<Integer,  StatusGroupDataEnum>();

    static {
        for (StatusGroupDataEnum st : StatusGroupDataEnum.values()) {
            mStatus.put(st.key(), st);
        }
    }

    private Integer key() {
        return this.key;
    }
    public static Map<Integer, StatusGroupDataEnum> getMap() {
        return mStatus;
    }

}
