package com.bepay.application.entities.db;

import com.bepay.application.constant.AppConst;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = AppConst.FEATURE_CODE_TABLE_NAME)
public class FeatureCodeEntity {

    @Id
    @Column(name = "ID", nullable = false)
    private BigDecimal id;

    @Column(name = "FEATURE_CODE")
    private String featureCode;

    @Column(name = "DATA_FORMAT")
    private String dataFormat;

    @Column(name = "STATUS")
    private Integer status;


}
