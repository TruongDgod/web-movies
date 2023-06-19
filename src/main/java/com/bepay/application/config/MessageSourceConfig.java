/*******************************************************************************
 * Class        MessageSourceConfig
 * Created date 8/3/2022
 * Updated date
 * Author       thanhsang1999
 * Change log   8/3/2022 thanhsang1999 Create New MessageSourceConfig
 ****************************************************************************/
package com.bepay.application.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/*
 * @see MessageSourceConfig
 * @author thanhsang1999
 */
@Configuration
public class MessageSourceConfig {
    @Bean(name = "messageSource")
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:i18n/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }
}
