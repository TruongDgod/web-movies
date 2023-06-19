package com.bepay.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSegmentDTO {
    private Integer appType;
    private Integer platform;
    private SegmentVersionDTO version;
}
