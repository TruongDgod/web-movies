package com.bepay.application.dto.request;

import com.bepay.application.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataPackageAddRequest {
    private BigDecimal id;
    private String code;
    private BigDecimal serviceGroup;
    private Long value;
    private BigDecimal role;
    private Boolean status;
    private ImageDTO image;
    private String nameEn;
    private String subEn;
    private String descEn;
    private String nameFr;
    private String subFr;
    private String descFr;
    private String nameRn;
    private String subRn;
    private String descRn;
}

