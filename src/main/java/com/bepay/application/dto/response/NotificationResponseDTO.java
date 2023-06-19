package com.bepay.application.dto.response;

import com.bepay.application.dto.FileUploadDTO;
import com.bepay.application.dto.TargetDTO;
import com.bepay.application.dto.UserSegmentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDTO {
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
    private Integer saveStatus;
    private FileUploadDTO file;
}
