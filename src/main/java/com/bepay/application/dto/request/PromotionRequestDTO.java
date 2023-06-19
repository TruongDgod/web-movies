package com.bepay.application.dto.request;

import com.bepay.application.dto.ImageDTO;
import com.bepay.application.dto.request.BaseRequest;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequestDTO extends BaseRequest {
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

    public String getFullContentEn() {
        return StringUtils.isBlank(fullContentEn) ? "" : fullContentEn;
    }

    public String getFullContentRn() {
        return StringUtils.isBlank(fullContentRn) ? "" : fullContentRn;
    }

    public String getFullContentFr() {
        return StringUtils.isBlank(fullContentFr) ? "" : fullContentFr;
    }
}
