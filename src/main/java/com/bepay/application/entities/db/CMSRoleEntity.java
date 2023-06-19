package com.bepay.application.entities.db;

import com.bepay.application.constant.AppConst;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = AppConst.CMS_ROLE_TABLE_NAME)
public class CMSRoleEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.SEQ_CMS_ROLE )
    @SequenceGenerator(name = AppConst.SEQ_CMS_ROLE , allocationSize = 1, sequenceName = AppConst.SEQ_CMS_ROLE )
    private BigDecimal id;
    @Column (name = "ROLE_NAME")
    String name;
    @Column (name = "STATUS")
    Integer status;
}
