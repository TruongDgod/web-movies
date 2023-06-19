/*******************************************************************************
 * Class        MySameSiteConfiguration
 * Created date 8/10/2022
 * Updated date
 * Author       thanhsang1999
 * Change log   8/10/2022 thanhsang1999 Create New MySameSiteConfiguration
 ****************************************************************************/
package com.bepay.application.config;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @see MySameSiteConfiguration
 * @author thanhsang1999
 */
@Configuration(proxyBeanMethods = false)
public class MySameSiteConfiguration {
    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofStrict();
    }
}
