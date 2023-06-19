package com.bepay.application.dto;

import com.bepay.application.constant.AppConst;
import com.bepay.application.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class AbstractDTO {

    private BigInteger id;

    private int isDeleted;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;


    public String getCreatedDateToString() {
        if (Objects.isNull(this.createdDate)) return "--";
        return DateUtil.convertDatetoString(this.createdDate, AppConst.DATE_FORMAT_YYYYMMDD_HH_MM_SS);
    }

    public String getUpdatedDateToString() {
        if (Objects.isNull(this.updatedDate)) return "--";
        return DateUtil.convertDatetoString(this.updatedDate, AppConst.DATE_FORMAT_YYYYMMDD_HH_MM_SS);
    }

    public String getCreatedByToView() {
        return StringUtils.isBlank(this.createdBy)?"--":this.createdBy;
    }

    public String getUpdatedByToView() {
        return StringUtils.isBlank(this.updatedBy)?"--":this.updatedBy;
    }
}
