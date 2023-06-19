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
@Table(name = AppConst.NEWS_TABLE_NAME)
public class NewsEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.SEQ_NEWS_NAME)
    @SequenceGenerator(name = AppConst.SEQ_NEWS_NAME, allocationSize = 1, sequenceName = AppConst.SEQ_NEWS_NAME)
    private BigDecimal id;

    @Column(name = "NEWS_TYPE")
    private String newsType;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CLIENT_TYPE")
    private String clientType;

    @Column(name = "START_TIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "END_TIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT_TYPE")
    private Integer contentType;
    //0: Url; 1: Html content
    @Column(name = "SHORT_CONTENT")
    private String shortContent;

    @Column(name = "FULL_CONTENT")
    private String fullContent;

    @Column(name = "CREATE_TIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    @Column(name = "UPDATE_TIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "SHOW_ON_TOP")
    private Integer showOnTop;

    @Column(name = "SHOW_AT_HOME")
    private Integer showAtHome;

    @Column(name = "HOT_NEWS")
    private Integer hotNews;

    @Column(name = "HOT_DEAL")
    private Integer hotDeal;

    @Column(name = "BIG_VOURCHER")
    private Integer bigVourcher;

    @Column(name = "PROMOTION")
    private Integer promotion;

    @Column(name = "TRANSACTION_NEWS")
    private Integer transactionNews;

    @Column(name = "TRANSACTION_DATA")
    private String transactionData;

    @Column(name = "MULTI_LANGUAGE")
    private Integer multiLanguage;

    @Column(name = "IS_DELETED")
    private Integer isDeleted;


}
