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
@Table(name = AppConst.GROUP_DATA_PACKAGE_TABLE_NAME)
public class DataPackageEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.SEQ_GROUP_DATA_PACKAGE )
    @SequenceGenerator(name = AppConst.SEQ_GROUP_DATA_PACKAGE , allocationSize = 1, sequenceName = AppConst.SEQ_GROUP_DATA_PACKAGE )
    private BigDecimal id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SUB_TITLE")
    private String subTitle;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "VALUE")
    private Long value;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "GROUP_DATA_ID")
    private BigDecimal groupDataId;

    @Column(name = "URL")
    private String url;

    @Column(name = "ROLE_ID")
    private BigDecimal roleId;

    @Column(name = "IS_DELETED")
    private Integer isDeleted;

}
