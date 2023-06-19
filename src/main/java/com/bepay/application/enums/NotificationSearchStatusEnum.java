package com.bepay.application.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
public enum NotificationSearchStatusEnum {
    ALL(0, Arrays.asList(1,2,3,4),"All"),
    DRAFT(1,Arrays.asList(1), "Draft"),
    READY(2,Arrays.asList(2),"Ready"),
    RUNNING(3,Arrays.asList(3),"Processing"),
    COMPLETED(4,Arrays.asList(4),"Completed");

    private final int id;
    private final List<Integer> key;
    private final String value;

    NotificationSearchStatusEnum(int id, List<Integer> key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public static Map<Integer,NotificationSearchStatusEnum> map = new HashMap<Integer,NotificationSearchStatusEnum>();

    static {
        for(NotificationSearchStatusEnum e : NotificationSearchStatusEnum.values()){
            map.put(e.id,e);
        }
    }
    public static Map<Integer,NotificationSearchStatusEnum> getMap() {
        return map;
    }
}
