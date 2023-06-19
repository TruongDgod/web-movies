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
@Table(name = AppConst. GROUP_DATA_TABLE_NAME)
public class DataGroupEntity  {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.SEQ_GROUP_DATA )
    @SequenceGenerator(name = AppConst.SEQ_GROUP_DATA , allocationSize = 1, sequenceName = AppConst.SEQ_GROUP_DATA )
    private BigDecimal id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SERVICE_CODE")
    private String serviceCode;

    @Column(name = "PARTNER_CODE")
    private String partnerCode;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "STATUS")
    private Integer status;

    @Column( name = "ROLE_ID")
    private BigDecimal roleId;

    @Column( name = "IS_DELETED")
    private Integer isDeleted;

}
