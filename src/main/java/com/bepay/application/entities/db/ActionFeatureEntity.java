package com.bepay.application.entities.db;

import com.bepay.application.constant.AppConst;
import com.bepay.application.entities.AbstractEntity;
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
@Table(name = AppConst.CMS_ACTION_FEATURE_TABLE_NAME)
public class ActionFeatureEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  AppConst.SEQ_CMS_ACTION_FEATURE)
    @SequenceGenerator(name = AppConst.SEQ_CMS_ACTION_FEATURE, allocationSize = 1, sequenceName = AppConst.SEQ_CMS_ACTION_FEATURE)
    @Column(name = "ID", nullable = false)
    private BigDecimal id;
    @Column(name = "TITLE")
    private String tilte;
    @Column(name = "VALUE")
    private String value;
}
