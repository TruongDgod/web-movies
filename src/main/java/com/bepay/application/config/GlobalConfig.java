package com.bepay.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "global") // prefix global, find global.* values
@PropertySource("classpath:global.properties")
public class GlobalConfig {
    private String name;
    private String passportUrl;
    private String userName;
    private String password;
    private String domainCode;
    private String domainCodeValue;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportUrl() {
        return passportUrl;
    }

    public void setPassportUrl(String passportUrl) {
        this.passportUrl = passportUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }

    public String getDomainCodeValue() {
        return domainCodeValue;
    }

    public void setDomainCodeValue(String domainCodeValue) {
        this.domainCodeValue = domainCodeValue;
    }
}
