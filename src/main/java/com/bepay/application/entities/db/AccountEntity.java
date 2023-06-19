package com.bepay.application.entities.db;

import com.bepay.application.constant.AppConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppConst.ACCOUNT_TABLE_NAME)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  AppConst.SEQ_ACCOUNT_NAME)
    @SequenceGenerator(name = AppConst.SEQ_ACCOUNT_NAME, allocationSize = 1, sequenceName = AppConst.SEQ_ACCOUNT_NAME)
    @Column(name = "ID", nullable = false)
    private BigDecimal id;

    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "PASSWORD")
    private  String passWord;

    public AccountEntity(String username, String password) {
        this.userName = username;
        this.passWord = password;
    }
}
