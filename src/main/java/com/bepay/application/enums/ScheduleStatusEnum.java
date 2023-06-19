package com.bepay.application.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Getter
public enum ScheduleStatusEnum {
    PUBLISH_NOW(0, "PUBLISH_NOW"),
    SCHEDULE(1, "SCHEDULE");

    private static final Map<Integer, ScheduleStatusEnum> mValues = new HashMap<>();
    private static final List<ScheduleStatusEnum> list ;


    private final Integer id;
    private final String type;

    ScheduleStatusEnum(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
    static {
        for (ScheduleStatusEnum s : ScheduleStatusEnum.values()) {
            mValues.put(s.id, s);
        }
        list = Stream.of(ScheduleStatusEnum.values())
                .map(ScheduleStatusEnum::getScheduleStatusEnum).collect(Collectors.toList());
    }

    public ScheduleStatusEnum getScheduleStatusEnum() {
        return this;
    }
    public static Map<Integer, ScheduleStatusEnum> getMap() {
        return mValues;
    }

    public static List<ScheduleStatusEnum> getList() {
        return list;
    }
}
