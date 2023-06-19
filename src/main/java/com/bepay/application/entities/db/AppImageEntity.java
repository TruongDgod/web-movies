package com.bepay.application.entities.db;

import com.bepay.application.constant.AppConst;
import com.bepay.application.entities.AbstractEntity;
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
@Table(name = AppConst.APP_IMAGE_TABLE_NAME)
public class AppImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.SEQ_APP_IMAGE_NAME)
    @SequenceGenerator(name = AppConst.SEQ_APP_IMAGE_NAME, allocationSize = 1, sequenceName = AppConst.SEQ_APP_IMAGE_NAME)
    @Column(name = "ID", nullable = false)
    private BigDecimal id;
    @Column(name = "LANGUAGE")
    private String language;
    @Column(name = "OBJ_TYPE")
    private String objType;
    @Column(name = "OBJ_ID")
    private BigDecimal objId;
    @Column(name = "OBJ_VALUE")
    private String objValue;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "IMAGE_TYPE")
    private String imageType;
    @Column(name = "URL")
    private String url;
    @Column(name = "THUMB_URL")
    private String thumbUrl;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CREATE_TIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createTime = new Date();
    @Column(name = "UPDATE_TIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateTIme;
}
