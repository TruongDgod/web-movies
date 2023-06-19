package com.bepay.application.repository.db;

import com.bepay.application.entities.db.CMSScheduleMassageEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ScheduleMessageRepository  extends AbstractRepository<CMSScheduleMassageEntity, Long> {

    @Query(value = "select se from CMSScheduleMassageEntity se where se.messageId = :messageId")
    CMSScheduleMassageEntity findByMessageId(@Param("messageId") BigDecimal id);

    @Query(value = "update CMSScheduleMassageEntity sm set sm.isDeleted = 1 where sm.messageId = :messageId")
    void setDeleteByMessageId(@Param("messageId") BigDecimal id);

}
