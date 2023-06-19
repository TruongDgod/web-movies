package com.bepay.application.entities.db;

import com.bepay.application.constant.AppConst;
import com.bepay.application.entities.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = AppConst.SCHEDULE_MESSAGE_TABLE_NAME)
public class CMSScheduleMassageEntity extends AbstractEntity {
    @Id
    @SequenceGenerator(name = AppConst.SEQ_SCHEDULE_MESSAGE_NAME, allocationSize = 1, sequenceName = AppConst.SEQ_SCHEDULE_MESSAGE_NAME)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConst.SEQ_SCHEDULE_MESSAGE_NAME)
    @Column(name = "ID", nullable = false)
    private BigDecimal id;

    @Column(name = "CMS_MESAGE_ID")
    private BigDecimal messageId;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "REPEAT_NUMBER")
    private Integer repeatNumber;

    @Column(name = "DAY_OF_WEEK")
    private String dayOfWeek;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "REPEAT_TIME")
    private Date repeatTime;

}
