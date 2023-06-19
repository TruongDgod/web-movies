/*******************************************************************************
 * Class        WebConfigurationConfig
 * Created date 8/4/2022
 * Updated date
 * Author       thanhsang1999
 * Change log   8/4/2022 thanhsang1999 Create New WebConfigurationConfig
 ****************************************************************************/
package com.bepay.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @see WebConfigurationConfig
 * @author thanhsang1999
 */
@Configuration
public class WebConfigurationConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowCredentials(true)
        ;
    }
}
