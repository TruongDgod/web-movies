package com.bepay.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDTO {
    private String fileName;
    private BigDecimal fileLength;
    private BigDecimal fileModified;
    private Boolean isFake;
}
