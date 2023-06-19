package com.bepay.application.utils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigInteger;

/**
 * @author DanTran
 * @return
 * @see XMLUtil
 */

public class XMLUtil {
    private XMLUtil() {
    }

    public static String getTextValue(Element ele, String tagName) {
        NodeList nodeList = ele.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            try {
                Element elm = (Element) nodeList.item(0);
                if (elm.getFirstChild() != null) {
                    return elm.getFirstChild().getNodeValue();
                }
            } catch (DOMException var4) {
            }
        }

        return "";
    }

    public static int getIntValue(Element ele, String tagName) {
        if (ele != null && tagName != null) {
            String tmp = getTextValue(ele, tagName);
            if (tmp != null) {
                try {
                    return Integer.parseInt(tmp);
                } catch (NumberFormatException var4) {
                }
            }
        }

        return -1;
    }

    public static long getLongValue(Element ele, String tagName) {
        if (ele != null && tagName != null) {
            String tmp = getTextValue(ele, tagName);
            if (tmp != null) {
                try {
                    return Long.parseLong(tmp);
                } catch (NumberFormatException var4) {
                }
            }
        }

        return -1L;
    }

    public static BigInteger getBigInteger(Element ele, String tagName) {
        BigInteger bigInteger;
        if (ele != null && tagName != null) {
            String tmp = getTextValue(ele, tagName);
            if (tmp != null) {
                try {
                    return new BigInteger(tmp);
                } catch (NumberFormatException var4) {
                }
            }
        }

        return BigInteger.valueOf(-1);
    }


}
