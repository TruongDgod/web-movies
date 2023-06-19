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
public class DataGroupRespone {

    private BigDecimal id;

    private String groupName;

    private String role;

    private String groupCode;

    private Boolean status;

    private String statusMessage;
}
