/*******************************************************************************
 * Class        RandomStringUtil
 * Created date 2021/11/11
 * Lasted date
 * Author       VIDAL
 * Change log   2021/11/11 VIDAL Create New
 ******************************************************************************/
package com.bepay.application.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

public class RandomStringUtil {
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String specials = "~=+%^*/()[]{}/!@#$?|";
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static final String ALL = alpha + alphaUpperCase + digits + specials;

    private static Random generator = new Random();

    /**
     * randomAlphaNumeric
     * Random Random string with a-zA-Z0-9, not included special characters
     * @param numberOfCharactor {@link Integer}
     *
     * @return String
     */
    public static String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * randomAll
     * Random Random string with a-zA-Z0-9, included special characters
     * @param numberOfCharactor {@link Integer}
     *
     * @return String
     */
    public String randomAll(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALL.length() - 1);
            char ch = ALL.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * randomCode
     * @param prefix {@link String}
     *
     * @return String
     */
    public static String randomCode(String prefix) throws ParseException, InterruptedException {
        StringBuilder sb = new StringBuilder(prefix.toUpperCase());
//        String formatIn = "dd-MM-yy-HH.mm.ss.SS";
//        String formatOut = "dd-MM-yy'T'HH:mm:ss.SSz";
//        String valueIn = DateUtil.convertDatetoString(new Date(),"dd-MM-yy-HH.mm.ss.SS");
//        SimpleDateFormat in = new SimpleDateFormat(formatIn);
//        SimpleDateFormat out = new SimpleDateFormat(formatOut);
////        Date dateIn = in.parse(valueIn);
////        String valueOut = out.format(dateIn);
////        System.out.println("> " + valueOut);
//        in.setTimeZone(TimeZone.getTimeZone("GMT"));
//        Date dateIn = in.parse(valueIn);
////        System.out.println("< " + dateIn);
//        out.setTimeZone(TimeZone.getTimeZone("GMT"));
//        String valueOut = out.format(dateIn);
//      System.out.println("> " + valueOut);
        String pattern = "dd-MM-yy-HH:mm:ss.SSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        Thread.sleep(1);
        String result = date.replaceAll("[-+.^:,GMT]","");
        sb.append(result);
        return sb.toString();
    }

    /**
     * randomPassword
     * Random string password with at least 1 digit and 1 special character
     * @param numberOfCharacter {@link Integer}
     *
     * @return String
     */
    public String randomPassword(int numberOfCharacter) {
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s -> {
            int number = randomNumber(0, s.length() - 1);
            result.add("" + s.charAt(number));
        };
        appendChar.accept(digits);
        appendChar.accept(specials);
        while (result.size() < numberOfCharacter) {
            appendChar.accept(ALL);
        }
        Collections.shuffle(result, generator);
        return String.join("", result);
    }

    /**
     * randomNumber
     * Random string password with at least 1 digit and 1 special character
     * @param min {@link Integer}
     * @param max {@link Integer}
     *
     * @return int
     */
    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

}
