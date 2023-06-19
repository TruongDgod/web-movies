package com.bepay.application.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO  {
    private BigDecimal id;

    private BigDecimal newsId;

    private String title;

    private String content;

    private String category;

    private Boolean languageStatus;

    private String languageStatusName;

    private String createTime;

    private String publishTime;

    private String status;

}
