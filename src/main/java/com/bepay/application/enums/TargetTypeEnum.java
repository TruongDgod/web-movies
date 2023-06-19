package com.bepay.application.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum TargetTypeEnum {
    USER_LIST(1, "USER_LIST"),
    USER_SEGMENT(2, "USER_SEGMENT");

    private static final Map<Integer, TargetTypeEnum> mValues = new HashMap<>();
    private static final List<TargetTypeEnum> list ;


    private final Integer id;
    private final String type;

    TargetTypeEnum(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
    static {
        for (TargetTypeEnum s : TargetTypeEnum.values()) {
            mValues.put(s.id, s);
        }
        list = Stream.of(TargetTypeEnum.values())
                .map(TargetTypeEnum::getTargetTypeEnum).collect(Collectors.toList());
    }

    public TargetTypeEnum getTargetTypeEnum() {
        return this;
    }
    public static Map<Integer, TargetTypeEnum> getMap() {
        return mValues;
    }

    public static List<TargetTypeEnum> getList() {
        return list;
    }
}
