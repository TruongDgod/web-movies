package com.bepay.application.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Getter
public enum SaveTypeEnum {
    PUBLISH_NOW(0, "PUBLISH"),
    DRAFT(1, "ADD_DRAFT");

    private static final Map<Integer, SaveTypeEnum> mValues = new HashMap<>();
    private static final List<SaveTypeEnum> list ;


    private final Integer id;
    private final String type;

    SaveTypeEnum(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
    static {
        for (SaveTypeEnum s : SaveTypeEnum.values()) {
            mValues.put(s.id, s);
        }
        list = Stream.of(SaveTypeEnum.values())
                .map(SaveTypeEnum::getSaveTypeEnum).collect(Collectors.toList());
    }

    public SaveTypeEnum getSaveTypeEnum() {
        return this;
    }
    public static Map<Integer, SaveTypeEnum> getMap() {
        return mValues;
    }

    public static List<SaveTypeEnum> getList() {
        return list;
    }
}
