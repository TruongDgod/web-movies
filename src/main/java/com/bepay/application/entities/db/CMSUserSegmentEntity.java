package com.bepay.application.entities.db;

import com.bepay.application.constant.AppConst;
import com.bepay.application.entities.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = AppConst.SEGMENT_TABLE_NAME )
public class CMSUserSegmentEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.SEQ_USER_SEGMENT_SEQ_NAME)
    @SequenceGenerator(name = AppConst.SEQ_USER_SEGMENT_SEQ_NAME, allocationSize = 1, sequenceName = AppConst.SEQ_USER_SEGMENT_SEQ_NAME)
    @Column(name = "ID", nullable = false)
    private BigDecimal id;

    @Column(name = "CMS_MESSAGE_ID")
    private BigDecimal messageID;

    @Column(name = "APP_NAME")
    private Integer appName;

    @Column(name = "VERSION_CONDITION")
    private String versionCondition;

    @Column(name = "PLATFORM")
    private Integer platform;

}
