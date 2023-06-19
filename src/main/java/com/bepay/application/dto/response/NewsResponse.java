package com.bepay.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
    private BigDecimal id;
    private String title;

    private String content;

    private String role;

    private String type;

    private String createTime;

    private Boolean multiLanguage;

    private String multiLanguageMessage;

    private String status;

    private String showAtHome;

    private String showOnTop;

    private String hotNews;

}
