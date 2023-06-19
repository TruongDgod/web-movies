package com.bepay.application.dto.request;

import com.bepay.application.constant.AppConst;
import com.bepay.application.utils.DateUtil;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class BaseRequest {
    private Integer page;
    private Integer size;
    private String lang;
    private Date fromDate;
    private Date toDate;
    public String toLogString() {
        try {
            Gson gson = new Gson();
            String req = gson.toJson(this);
            if (req != null) {
                req = req.replaceAll("(\"pin\":\"[0-9]{6}\")", "\"pin\":\"******\"");
            }
            return req;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "No Request";
    }

    public String logStringRequest() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Date getStartFromDate() {
        if (Objects.isNull(this.fromDate)) return null;
        return DateUtil.atStartOfDay(this.fromDate);
    }

    public Date getEndToDate() {
        if (Objects.isNull(this.toDate)) return null;
        return DateUtil.atEndOfDay(this.toDate);
    }

}
