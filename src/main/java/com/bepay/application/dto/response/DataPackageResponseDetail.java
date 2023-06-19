package com.bepay.application.dto.response;

import com.bepay.application.dto.ImageDTO;
import com.bepay.application.dto.ServiceGroupDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataPackageResponseDetail {
    private BigDecimal id;
    private String code;
    private ServiceGroupDTO serviceGroup;
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
