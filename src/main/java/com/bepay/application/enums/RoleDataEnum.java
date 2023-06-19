package com.bepay.application.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum RoleDataEnum {
    ALL(0, "ALL", "All", Arrays.asList(new BigDecimal(4), new BigDecimal(1), new BigDecimal(63))),
    END_USER(4, "END_USER_APP", "End User", Arrays.asList(new BigDecimal(4))),
    AGENT(1, "AGENT", "Agent ", Arrays.asList(new BigDecimal(1))),
    MERCHANT(63, "MERCHANT", "Merchant", Arrays.asList(new BigDecimal(63)));
    private static final Map<Integer, RoleDataEnum> map = new HashMap<>();
    private static final Map<String, RoleDataEnum> mapCode = new HashMap<>();
    private static final List<RoleDataEnum> list;


    private final Integer id;
    private final String code;
    private final String desc;

    private final List<BigDecimal> listSearch;

    static {
        for (RoleDataEnum s : RoleDataEnum.values()) {
            map.put(s.id, s);
            mapCode.put(s.code, s);
        }

        list = Stream.of(RoleDataEnum.values())
                .map(RoleDataEnum::getTransactionTypeEnum).collect(Collectors.toList());
    }

    RoleDataEnum(Integer id, String code, String desc, List<BigDecimal> listStr) {
        this.id = id;
        this.code = code;
        this.desc = desc;
        this.listSearch = listStr;
    }

    public RoleDataEnum getTransactionTypeEnum() {
        return this;
    }

    public static Map<Integer, RoleDataEnum> getMap() {
        return map;
    }

    public static RoleDataEnum getDefault(BigDecimal id) {
        try {
            return Optional.of(map.get(id.intValue())).orElse(RoleDataEnum.ALL);
        } catch (Exception e) {
            return RoleDataEnum.ALL;
        }
    }

    public static Map<String, RoleDataEnum> getMapCode() {
        return mapCode;
    }

    public static List<RoleDataEnum> getList() {
        return list;
    }
}
