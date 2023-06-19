package com.bepay.application.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
public class LocaleConfig {
    private static LocaleConfig localeConfig;
    private Locale locale;

    public LocaleConfig(Locale locale) {
        this.locale = locale;
    }

    public static LocaleConfig getInstance() {
        if (localeConfig == null) {
            localeConfig = new LocaleConfig(Locale.US);
        }
        return localeConfig;
    }

}
