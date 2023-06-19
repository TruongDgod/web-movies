/*******************************************************************************
 * Class        MessageSourceConfig
 * Created date 8/3/2022
 * Updated date
 * Author       thanhsang1999
 * Change log   8/3/2022 thanhsang1999 Create New MessageSourceConfig
 ****************************************************************************/
package com.bepay.application.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/*
 * @see MessageSourceConfig
 * @author thanhsang1999
 */
@Configuration
public class MessageUtil {
    private static MessageSource messageSource;

    @Autowired
    MessageUtil(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    public static String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    public static String getMessage(String key, String lang) {
        Locale locale;
        if (StringUtils.isBlank(lang)) lang = "en";
        switch (lang) {
            case "vi":
                locale = new Locale("vi", "VN");
                break;
            case "km":
                locale = new Locale("km", "KH");
                break;
            default:
                locale = new Locale("en", "US");

        }
        return messageSource.getMessage(key, null, locale);
    }
}
