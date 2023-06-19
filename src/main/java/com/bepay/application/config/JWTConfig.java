package com.bepay.application.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api-config")
@PropertySource("classpath:jwt-config.properties")
@Getter
@Setter
@NoArgsConstructor
public class JWTConfig {
    private Integer expired;
    private String secretKey;
    private String subject;
    private String issuer;

}
