package com.bepay.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDataPackageRequest extends BaseRequest {
    private String name;
    private BigDecimal groupDataId;
}
