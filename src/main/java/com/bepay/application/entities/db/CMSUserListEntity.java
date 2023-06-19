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
@Table(name = AppConst.USER_LIST_TABLE_NAME)
public class CMSUserListEntity extends AbstractEntity {
    @Id
    @SequenceGenerator(name = AppConst.SEQ_USER_LIST_NAME, allocationSize = 1, sequenceName = AppConst.SEQ_USER_LIST_NAME)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  AppConst.SEQ_USER_LIST_NAME )
    @Column(name = "ID", nullable = false)
    private BigDecimal id;

    @Column(name = "MESSAGE_ID" )
    private BigDecimal messageID;

    @Column(name = "PHONE_NUMBER")
    private BigDecimal phoneNumber;
}
