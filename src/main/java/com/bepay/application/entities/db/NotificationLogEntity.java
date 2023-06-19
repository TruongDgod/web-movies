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
@Table(name = AppConst.NOTIFICATION_LOG_TABLE_NAME)
public class NotificationLogEntity {

     @Id
     @Column(name = "ID", nullable = false)
     @SequenceGenerator(name = AppConst.NOTIFICATION_LOG_SEQ, allocationSize = 1, sequenceName = AppConst.NOTIFICATION_LOG_SEQ)
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.NOTIFICATION_LOG_SEQ)
     private BigDecimal id;

     @Column(name = "REF_ID ")
     private String refId;
     @Column(name = "APP_USER_ID ")
     private Integer appUserID;
     @Column(name = "MSISDN ")
     private String  msisdn;
     @Column(name = "STATUS ")
     private Integer status;
     @Column(name = "CLIENT_TYPE ")
     private Integer clientType;
     @Column(name = "OBJ_TYPE ")
     private String objType;
     @Column(name = "OBJ_ID ")
     private Integer objId;
     @Column(name = "OBJ_VALUE ")
     private String objValue;
     @Column(name = "NOTIFICATION_TYPE ")
     private Integer notificationType;
     @Column(name = "TITLE ")
     private String title;
     @Column(name = "SHORT_CONTENT ")
     private String shortContent;
     @Column(name = "FULL_CONTENT ")
     private String fullContent;
     @Column(name = "OBJ_DATA ")
     private String objDate;
     @Column(name = "CREATE_TIME ")
     private Date createTime;
     @Column(name = "UPDATE_TIME  ")
     private Date updateTime;
     @Column(name = "ACCOUNT_ID ")
     private String accountId;
     @Column(name = "TRANSACTION_ID ")
     private String transactionId;

}
