package com.bepay.application.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum CategoryTypeEnum {
    ALL(3, "All"),
    NOTIFICATION(1, "Notification"),
    PROMOTION(2, "Promotion");

    private static final Map<Integer, CategoryTypeEnum> mValues = new HashMap<>();
    private static final List<CategoryTypeEnum> list ;


    private final Integer id;
    private final String type;

    CategoryTypeEnum(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
    static {
        for (CategoryTypeEnum s : CategoryTypeEnum.values()) {
            mValues.put(s.id, s);
        }
        list = Stream.of(CategoryTypeEnum.values())
                .map(CategoryTypeEnum::getTargetTypeEnum).collect(Collectors.toList());
    }

    public CategoryTypeEnum getTargetTypeEnum() {
        return this;
    }
    public static Map<Integer, CategoryTypeEnum> getMap() {
        return mValues;
    }

    public static List<CategoryTypeEnum> getList() {
        return list;
    }
}
