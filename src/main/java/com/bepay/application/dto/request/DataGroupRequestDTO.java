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
public class DataGroupRequestDTO extends BaseRequest{

    private BigDecimal id;

    private String code;

    private BigDecimal role;

    private Boolean status;

    private String nameEn;

    private String nameFr;

    private String nameRn;


}
