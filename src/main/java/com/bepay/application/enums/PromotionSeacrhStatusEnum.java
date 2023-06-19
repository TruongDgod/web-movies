package com.bepay.application.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Getter
public enum PromotionSeacrhStatusEnum {
    IN_ACTIVE(0, Arrays.asList(0),"in active"),
    ACTIVE(1, Arrays.asList(1),"active"),
    ALL(2, Arrays.asList(1,0),"all");

    private final Integer id;
    private final List<Integer> value;
    private final String desc;

    PromotionSeacrhStatusEnum(Integer id, List<Integer> value, String desc) {
        this.id = id;
        this.value = value;
        this.desc = desc;
    }

    public static Map<Integer,PromotionSeacrhStatusEnum> map = new HashMap<Integer,PromotionSeacrhStatusEnum>();
    static {
        for (PromotionSeacrhStatusEnum s : PromotionSeacrhStatusEnum.values()) {
            map.put(s.id, s);
        }
    }
    public static Map<Integer, PromotionSeacrhStatusEnum> getMap() {
        return map;
    }

}
