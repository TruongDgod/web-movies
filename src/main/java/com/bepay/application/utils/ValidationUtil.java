/*******************************************************************************
 * Class        ValidationUtil
 * Created date 2021/11/11
 * Lasted date
 * Author       VIDAL
 * Change log   2021/11/11 VIDAL Create New
 ******************************************************************************/
package com.bepay.application.utils;

import com.bepay.application.exceptions.BadRequestException;
import java.util.Date;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.apache.commons.lang3.StringUtils;

public class ValidationUtil {

    static String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    /**
     * isEmailValid
     * @param email
     *
     * @return boolean
     */
    public static boolean isEmailValid(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        } else {
            return email.matches(EMAIL_REGEX);

        }
    }

    /**
     * validateDate
     * validate start date is before end date
     * @param startDate {@link Date}
     * @param endDate {@link Date}
     *
     * @return boolean
     */
    public boolean validateDate(Date startDate, Date endDate) {
        boolean before = endDate.before(startDate);
        if (before) {
            return false;
        }
        return true;
    }

    /**
     * validArabic
     * arabic validation
     * @param s {@link String}
     *
     * @return boolean
     */
    public static boolean validArabic(String s) {
        for (int i = 0; i < s.length(); ) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    public static boolean containNumber(String password) {
        return password.matches(".*\\d.*");
    }

    public static boolean containLowerCase(String password) {
        return password.matches(".*[a-z].*");
    }

    public static boolean containUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }

    public static boolean containSpecialCharacters(String password) {
        if (password.matches(".*[{}<>()`~!@#$%^&*-+=:;'\".,?\\/|].*"))
            return true;
        if (password.matches(".*\\[.*"))
            return true;
        if (password.matches(".*\\].*"))
            return true;
        if (password.matches(".*\\s.*"))
            return true;
        return false;
    }


    /**
     * isNumberValid
     * Method to validate mobile number based on min and max length
     * @param number {@link String}
     * @param minLength {@link Integer}
     * @param maxLength {@link Integer}
     *
     * @return boolean
     */
    public static boolean isNumberValid(String number, int minLength, int maxLength) {
        if (StringUtils.isEmpty(number)) {
            return false;
        } else {
            String regex = "\\d{" + minLength + "," + maxLength + "}";
            if (number.matches(regex)) {
                return true;
            } else {
                return false;
            }

        }
    }

    /**
     * validatePhoneNumber
     *
     * @param phoneNumber {@link String}
     *
     * @return void
     */
    public static void validatePhoneNumber(String phoneNumber) {

        if (phoneNumber.startsWith("0") || !phoneNumber.startsWith("+"))
            throw new BadRequestException("the phone number is not valid");

        boolean numeric = isNumeric(phoneNumber);
        if (!numeric || phoneNumber.contains(".") || phoneNumber.contains(","))
            throw new BadRequestException("the phone number is not valid");

        PhoneNumber phone = null;
        boolean isValidNumber = false;
        boolean isSANumber = false;

        PhoneNumberUtil pnUtil = PhoneNumberUtil.getInstance();
        try {

            phone = pnUtil.parse(phoneNumber, "SA");
            isValidNumber = pnUtil.isValidNumber(phone);
            isSANumber = pnUtil.getRegionCodeForNumber(phone).equals("SA");
        } catch (Exception e) {
            throw new BadRequestException("the phone code is not valid");
        }

        if (!isSANumber || !isValidNumber)
            throw new BadRequestException("the phone number is not valid");
        else {

            if (  phoneNumber.length() != 13 )
                throw new BadRequestException("the phone number length is invalid");
        }
    }

    /**
     * isNumeric
     * only contains number
     *
     * @param s {@link String}
     *
     * @return boolean
     */
    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * isNumber
     * Method to validate mobile number based on min and max length
     *
     * @param number {@link String}
     * @param minLength {@link Integer}
     * @param maxLength {@link Integer}
     *
     * @return void
     */
    public static boolean isNumber(String number, int minLength, int maxLength) {
        if (StringUtils.isEmpty(number)) {
            return false;
        } else {
            String regex = "\\d{" + minLength + "," + maxLength + "}";
            if (number.matches(regex)) {
                return true;
            } else {
                return false;
            }

        }
    }

    /**
     * isInteger
     *
     * @param value {@link String}
     *
     * @return boolean
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * isAlphaNumeric
     *
     * @param value {@link String}
     *
     * @return boolean
     */
    public static boolean isAlphaNumeric(String value) {
        String PATTERN = "^[0-9a-zA-Z]+$";
        return value.matches(PATTERN);
    }

}
