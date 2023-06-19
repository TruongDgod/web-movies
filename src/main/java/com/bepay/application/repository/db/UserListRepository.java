package com.bepay.application.repository.db;

import com.bepay.application.entities.db.CMSUserListEntity;
import com.bepay.application.entities.db.CMSUserSegmentEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserListRepository extends AbstractRepository<CMSUserListEntity, BigDecimal> {
    @Query(value = "select us from CMSUserListEntity us where us.messageID = :messageId and us.isDeleted = 0")
    List<CMSUserListEntity> findByMessageId(@Param("messageId") BigDecimal id);

    @Query(value = "update  CMSUserListEntity us set us.isDeleted = 1 where us.messageID = :messageId")
    void setDeleteByMessageId(@Param("messageId") BigDecimal id);
}
