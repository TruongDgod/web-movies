package com.bepay.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataPackageResponeDTO {
    private BigDecimal id;
    private String name;
    private String role;
    private String code;
    private String groupName;
    private Boolean status;
    private String statusMessage;
}
