package com.bepay.application.dto.request;

import com.bepay.application.dto.FileUploadDTO;
import com.bepay.application.dto.TargetDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO extends BaseRequest {
    private BigDecimal id;
    private BigDecimal newsId;
    private Integer categoryType;
    private Boolean multiLanguage;
    private String titleEn;
    private String titleFr;
    private String titleRn;
    private String contentEn;
    private String contentRn;
    private String contentFr;
    private Integer targetType;
    private TargetDTO target;
    private Integer scheduleStatus;
    private Date scheduleTime;
    private Integer saveType;
    private FileUploadDTO file;
}
