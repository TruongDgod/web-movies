/*******************************************************************************
 * Class        RuleMatchUtil
 * Created date 2021/11/11
 * Lasted date
 * Author       VIDAL
 * Change log   2021/11/17 VIDAL Create New
 ******************************************************************************/
package com.bepay.application.utils;


public class RuleMatchUtil {

    public static void main(String arg[]) {
        //Provide source and target strings to lock_match function to compare--//
        System.out.println("Your Strings are Matched=" + transform("The warlock", "The warlock") + "%");
        System.out.println("Your Strings are Matched=" + transform("The warlock", "The lock l") + "%");
    }

    /**
     * lockMatch
     * @param source
     * @param target
     *
     * @return int
     */
    public static int transform(String source, String target) {
        int total_w = wordCount(source);
        int total = 100;
        int per_w = total / total_w;
        int gotperW = 0;

        if (!source.equals(target)) {

            for (int i = 1; i <= total_w; i++) {
                if (simpleMatch(splitString(source, i), target) == 1) {
                    gotperW = ((per_w * (total - 10)) / total) + gotperW;
                } else if (frontFullMatch(splitString(source, i), target) == 1) {
                    gotperW = ((per_w * (total - 20)) / total) + gotperW;
                } else if (anywhereMatch(splitString(source, i), target) == 1) {
                    gotperW = ((per_w * (total - 30)) / total) + gotperW;
                } else {
                    gotperW = ((per_w * smartMatch(splitString(source, i), target)) / total) + gotperW;
                }
            }
        } else {
            gotperW = 100;
        }
        return gotperW;
    }

    /**
     * anywhereMatch
     * @param source
     * @param target
     *
     * @return int
     */
    public static int anywhereMatch(String source, String target) {
        int x = 0;
        if (target.contains(source)) {
            x = 1;
        }
        return x;
    }

    /**
     * frontFullMatch
     * @param source
     * @param target
     *
     * @return int
     */
    public static int frontFullMatch(String source, String target) {
        int x = 0;
        String tempt;
        int len = source.length();

        //----------Work Body----------//
        for (int i = 1; i <= wordCount(target); i++) {
            tempt = splitString(target, i);
            if (tempt.length() >= source.length()) {
                tempt = tempt.substring(0, len);
                if (source.contains(tempt)) {
                    x = 1;
                    break;
                }
            }
        }
        //---------END---------------//
        if (len == 0) {
            x = 0;
        }
        return x;
    }

    /**
     * simpleMatch
     * @param source
     * @param target
     *
     * @return int
     */
    public static int simpleMatch(String source, String target) {
        int x = 0;
        String tempt;
        int len = source.length();


        //----------Work Body----------//
        for (int i = 1; i <= wordCount(target); i++) {
            tempt = splitString(target, i);
            if (tempt.length() == source.length()) {
                if (source.contains(tempt)) {
                    x = 1;
                    break;
                }
            }
        }
        //---------END---------------//
        if (len == 0) {
            x = 0;
        }
        return x;
    }

    /**
     * smartMatch
     * @param ts
     * @param tt
     *
     * @return int
     */
    public static int smartMatch(String ts, String tt) {

        char[] s = new char[ts.length()];
        s = ts.toCharArray();
        char[] t = new char[tt.length()];
        t = tt.toCharArray();


        int slength = s.length;
        //number of 3 combinations per word//
        int combs = (slength - 3) + 1;
        //percentage per combination of 3 characters//
        int ppc = 0;
        if (slength >= 3) {
            ppc = 100 / combs;
        }
        //initialising an integer to store the total % this class genrate//
        int x = 0;
        //declaring a temporary new source char array
        char[] ns = new char[3];
        //check if source char array has more then 3 characters//
        if (slength < 3) {
        } else {
            for (int i = 0; i < combs; i++) {
                for (int j = 0; j < 3; j++) {
                    ns[j] = s[j + i];
                }
                if (crossFullMatch(ns, t) == 1) {
                    x = x + 1;
                }
            }
        }
        x = ppc * x;
        return x;
    }

    /**
     * crossFullMatch
     * @param source
     * @param target
     *
     * @return int
     */
    public static int crossFullMatch(char[] source, char[] target) {
        int z = target.length - source.length;
        int x = 0;
        if (source.length > target.length) {
            return x;
        } else {
            for (int i = 0; i <= z; i++) {
                for (int j = 0; j <= (source.length - 1); j++) {
                    if (source[j] == target[j + i]) {
                        // x=1 if any charecer matches
                        x = 1;
                    } else {
                        // if x=0 mean an character do not matches and loop break out
                        x = 0;
                        break;
                    }
                }
                if (x == 1) {
                    break;
                }
            }
        }
        return x;
    }

    /**
     * splitString
     * @param source
     * @param n
     *
     * @return String
     */
    public static String splitString(String source, int n) {

        int index;
        String temp;
        temp = source;
        String temp2 = null;

        int temp3 = 0;

        for (int i = 0; i < n; i++) {
            int slength = temp.length();
            index = temp.indexOf(" ");
            if (index < 0) {
                index = slength;
            }
            temp2 = temp.substring(temp3, index);
            temp = temp.substring(index, slength);
            temp = temp.trim();

        }
        return temp2;
    }

    /**
     * splitString
     * @param source
     *
     * @return int
     */
    public static int wordCount(String source) {
        int x = 1;
        int c;
        source = source.trim();
        if (source.isEmpty()) {
            x = 0;
        } else {
            if (source.contains(" ")) {
                for (; ; ) {
                    x++;
                    c = source.indexOf(" ");
                    source = source.substring(c);
                    source = source.trim();
                    if (source.contains(" ")) {
                    } else {
                        break;
                    }
                }
            }
        }
        return x;
    }
}
