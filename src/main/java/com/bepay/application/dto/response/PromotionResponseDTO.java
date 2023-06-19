package com.bepay.application.dto.response;

import com.bepay.application.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionResponseDTO {
    private BigDecimal id;
    private Integer newsType;
    private Integer roleType;
    private Date startTime;
    private Date endTime;
    private Boolean status;
    private Boolean showAtHome;
    private Boolean showOnTop;
    private Boolean hotNews;
    private Boolean multiLanguage;
    private String titleEn;
    private String titleFr;
    private String titleRn;
    private String shortContentEn;
    private String shortContentFr;
    private String shortContentRn;
    private String fullContentEn;
    private String fullContentRn;
    private String fullContentFr;
    private ImageDTO imageBanner;
    private ImageDTO imageHeader;
    private ImageDTO imageAvatar;
    private String transactionData;
    private Integer actionType;

}
