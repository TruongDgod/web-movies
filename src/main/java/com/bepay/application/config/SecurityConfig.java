package com.bepay.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security")
@PropertySource("classpath:viettelsecurity.properties")
@Getter
@Setter
public class SecurityConfig {
    private String key;
}
