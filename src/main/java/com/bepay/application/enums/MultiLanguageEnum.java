/*******************************************************************************
 * Class        TransactionTypeEnum
 * Created date 22/12/2021
 * Lasted date
 * Author       thanhsang1999
 * Change log   22/12/2021 thanhsang1999 Create New
 ******************************************************************************/
package com.bepay.application.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author thanhsang1999
 * @see MultiLanguageEnum
 */
@Getter
public enum MultiLanguageEnum {
    NO_MULTI_LANGUAGE(0, "OFF", false),
    MULTI_LANGUAGE(1, "ON", true);

    private static final Map<Integer, MultiLanguageEnum> mValues = new HashMap<>();
    private static final Map<Boolean, MultiLanguageEnum> mMulti = new HashMap<>();
    private static final List<MultiLanguageEnum> list;


    private final Integer id;
    private final String desc;
    private final Boolean isMulti;

    static {
        for (MultiLanguageEnum s : MultiLanguageEnum.values()) {
            mValues.put(s.id, s);
            mMulti.put(s.getIsMulti(),s);
        }
        list = Stream.of(MultiLanguageEnum.values())
                .map(MultiLanguageEnum::getTransactionTypeEnum).collect(Collectors.toList());
    }

    MultiLanguageEnum(Integer id, String desc, Boolean isMulti) {
        this.id = id;
        this.desc = desc;
        this.isMulti = isMulti;
    }

    public MultiLanguageEnum getTransactionTypeEnum() {
        return this;
    }

    public static MultiLanguageEnum get(Integer id) {
        return mValues.get(id);
    }

    public static MultiLanguageEnum get(Boolean id) {
        return mMulti.get(id);
    }

    public static List<MultiLanguageEnum> getList() {
        return list;
    }
}
