package com.bepay.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataGroupSearchRequest extends BaseRequest {
    private String name;
    private Integer status;
    private BigDecimal role;
}
