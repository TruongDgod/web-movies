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
@Table(name = AppConst.MESSAGE_TABLE_NAME)
public class CMSMessageEntity extends AbstractEntity {
    @Id
    @SequenceGenerator(name = AppConst.SEQ_MESSAGE_NAME, allocationSize = 1, sequenceName = AppConst.SEQ_MESSAGE_NAME)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.SEQ_MESSAGE_NAME)
    @Column(name = "ID", nullable = false)
    private BigDecimal id;

    @Column(name = "TITLE_ENGLISH" )
    private String titleEnglish;

    @Column(name = "CONTENT_ENGLISH")
    private String contentEnglish;

    @Column(name = "TITLE_KIRUNDI")
    private String titleKirundi;

    @Column(name = "CONTENT_KIRUNDI")
    private String contentKirundi;

    @Column(name = "TITLE_FRENCH")
    private String titleFrench;

    @Column(name = "CONTENT_FRENCH")
    private String contentFrench;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "TEST_STATUS")
    private Integer testStatus;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "SCHEDULE_STATUS")
    private Integer  scheduleStatus;

    @Column(name = "LIST_TEST_PHONE")
    private String listTestPhone;

    @Column(name = "CATEGORY")
    private Integer category;

    @Column(name = "TARGET_TYPE")
    private Integer targetType;

    @Column(name = "TIME_SEND")
    private Date timeSend;

    @Column(name = "PUSHED_TIME")
    private Date pushedTime;

    @Column(name = "IS_MULTI_LANGUAGE")
    private Integer isMultiLanguage;

    @Column(name = "NEWS_ID")
    private BigDecimal newsID;

}
