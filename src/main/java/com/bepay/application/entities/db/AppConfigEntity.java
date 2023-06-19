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
@Table(name = AppConst.APP_CONFIG_TABLE_NAME)
public class AppConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  AppConst.SEQ_APP_CONFIG_NAME)
    @SequenceGenerator(name = AppConst.SEQ_APP_CONFIG_NAME, allocationSize = 1, sequenceName = AppConst.SEQ_APP_CONFIG_NAME)
    @Column(name = "ID", nullable = false)
    private BigDecimal id;
    @Column(name = "LANGUAGE")
    private String language;
    @Column(name = "FEATURE_CODE")
    private String featureCode;
    @Column(name = "OBJ_TYPE")
    private String objType;
    @Column(name = "OBJ_ID")
    private BigDecimal objId;
    @Column(name = "OBJ_VALUE")
    private String objValue;
    @Column(name = "CONFIG_KEY")
    private String configKey;
    @Column(name = "CONFIG_VALUE")
    private String configValue;
    @Column(name = "CONFIG_NAME")
    private String configName;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "OBJ_DATA1")
    private String objData1;
    @Column(name = "OBJ_DATA2")
    private String objData2;
    @Column(name = "OBJ_DATA3")
    private String objData3;
    @Column(name = "OBJ_DATA4")
    private String objData4;
    @Column(name = "OBJ_DATA5")
    private String objData5;
    @Column(name = "DESCRIPTION")
    private Integer description;
}
