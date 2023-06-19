/*******************************************************************************
 * Class        TransactionTypeEnum
 * Created date 22/12/2021
 * Lasted date
 * Author       thanhsang1999
 * Change log   22/12/2021 thanhsang1999 Create New
 ******************************************************************************/
package com.bepay.application.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author thanhsang1999
 * @see GenderEnum
 */
public enum GenderEnum {
    MALE("0", "common.male", "0 IS MALE"),
    FEMALE("1", "common.female", "1 IS FEMALE");

    private static final Map<String, GenderEnum> mValues = new HashMap<String, GenderEnum>();
    private static final List<GenderEnum> list;


    private final String status;
    private final String code;
    private final String message;

    static {
        for (GenderEnum s : GenderEnum.values()) {
            mValues.put(s.status, s);
        }
        list = Stream.of(GenderEnum.values())
                .map(GenderEnum::getTransactionTypeEnum).collect(Collectors.toList());
    }

    GenderEnum(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public GenderEnum getTransactionTypeEnum() {
        return this;
    }

    public static Map<String, GenderEnum> getMap() {
        return mValues;
    }

    public static List<GenderEnum> getList() {
        return list;
    }
}
