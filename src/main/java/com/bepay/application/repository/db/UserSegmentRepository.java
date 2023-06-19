package com.bepay.application.repository.db;

import com.bepay.application.entities.db.CMSUserSegmentEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserSegmentRepository extends AbstractRepository<CMSUserSegmentEntity, Long> {
    @Query(value = "select us from CMSUserSegmentEntity us where us.messageID = :messageId and us.isDeleted = 0")
    List<CMSUserSegmentEntity> findByMessageId(@Param("messageId") BigDecimal id);
}
