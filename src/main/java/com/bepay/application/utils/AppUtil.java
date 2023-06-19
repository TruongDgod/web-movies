/*******************************************************************************
 * Class        AppUtil
 * Created date 2021/11/11
 * Lasted date
 * Author       VIDAL
 * Change log   2021/11/11 VIDAL Create New
 ******************************************************************************/
package com.bepay.application.utils;

import com.bepay.application.constant.AppConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static com.bepay.application.constant.AppConst.PHONE_NUMBER_SPEC_REG;

/**
 * @author vidal
 * @return
 * @see AppUtil
 */
public class AppUtil {
    /**
     * Checks if is collection empty.
     *
     * @param collection the collection
     * @return true, if is collection empty
     */
    private static boolean isCollectionEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is object empty.
     *
     * @param object the object
     * @return true, if is object empty
     */
    public static boolean isObjectEmpty(Object object) {
        if (object == null) return true;
        else if (object instanceof String) {
            if (((String) object).trim().length() == 0) {
                return true;
            }
        } else if (object instanceof Collection) {
            return isCollectionEmpty((Collection<?>) object);
        }
        return false;
    }

    public static boolean isNullOrEmpty(String input) {
        return input == null || "".equals(input.trim()) || "?".equals(input.trim());
    }

    public static boolean isLumitelNumber(String input) {
        String str = input.replaceAll(AppConst.PHONE_NUMBER_SPEC_REG, AppConst.BLANK);
        return validate(AppConst.PHONE_NUMBER_REG, input);
    }
    public static boolean validate(String reg, String input) {
        Pattern p = Pattern.compile(reg);
        return p.matcher(input).matches();
    }
}
