package com.bepay.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataPackageDTO {
    private BigDecimal id;
    private String name;
    private BigDecimal role;
    private String code;
    private String groupName;
    private Integer status;
}
