package com.bepay.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app-config")
@PropertySource("classpath:app-config.properties")
@Getter
@Setter
public class AppConfig {
    private Long alertExpiredDate;
}
