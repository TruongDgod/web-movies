package com.bepay.application.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum AppTypeEnum {
    END_USER(1, "END_USER_APP", "End User",Arrays.asList(1)),
    AGENT(2, "AGENT_MERCHANT", "Agent & Merchant",Arrays.asList(2)),
    BOTH(3, "BOTH", "Both",Arrays.asList(3)),
    ALL(4, "ALL", "All",Arrays.asList(1,2,3));
    private static final Map<Integer, AppTypeEnum> map = new HashMap<>();
    private static final Map<String, AppTypeEnum> mapCode = new HashMap<>();
    private static final List<AppTypeEnum> list;
    private final Integer id;
    private final String code;
    private final String desc;
    private final List<Integer> listSearch;

    static {
        for (AppTypeEnum s : AppTypeEnum.values()) {
            map.put(s.id, s);
            mapCode.put(s.code, s);
        }

        list = Stream.of(AppTypeEnum.values())
                .map(AppTypeEnum::getTransactionTypeEnum).collect(Collectors.toList());
    }

    AppTypeEnum(Integer id, String code, String desc, List<Integer> listStr) {
        this.id = id;
        this.code = code;
        this.desc = desc;
        this.listSearch = listStr;
    }

    public AppTypeEnum getTransactionTypeEnum() {
        return this;
    }

    public static Map<Integer, AppTypeEnum> getMap() {
        return map;
    }
    public static Map<String, AppTypeEnum> getMapCode() {
        return mapCode;
    }
    public static List<AppTypeEnum> getList() {
        return list;
    }
}
