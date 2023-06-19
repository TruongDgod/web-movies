package com.bepay.application.repository.db;

import com.bepay.application.entities.db.CMSFileUserListEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileUploadRepository extends AbstractRepository<CMSFileUserListEntity, BigDecimal> {
    @Query(value = "SELECT file " +
            " FROM CMSFileUserListEntity file" +
            " WHERE file.isDeleted = 0  " +
            " AND file.messageId = :messageId" )
    Optional<CMSFileUserListEntity> getByMessageId(BigDecimal messageId);

    List<CMSFileUserListEntity> findAllByMessageId(BigDecimal messageId);
}
