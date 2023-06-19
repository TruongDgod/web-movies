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
@Table(name = AppConst.CMS_FILE_USER_LIST)
public class CMSFileUserListEntity {
    @Id
    @SequenceGenerator(name = AppConst.CMS_FILE_USER_LIST_SEQ, allocationSize = 1, sequenceName = AppConst.CMS_FILE_USER_LIST_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.CMS_FILE_USER_LIST_SEQ)
    @Column(name = "ID", nullable = false)
    private BigDecimal id;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "FILE_LENGTH")
    private BigDecimal fileLength;
    @Column(name = "FILE_MODIFIED")
    private BigDecimal fileModified;
    @Column(name = "MESSAGE_ID")
    private BigDecimal messageId;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;


}
