package com.bepay.application.repository.db;

import com.bepay.application.entities.db.CMSMessageEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends AbstractRepository<CMSMessageEntity, BigDecimal> {
    @Query( value = "SELECT m FROM CMSMessageEntity m " +
            " WHERE (:fromDate IS NULL OR (:fromDate IS NOT NULL AND m.createdDate >= :fromDate ))" +
            " AND ( :toDate IS NULL OR (:toDate IS NOT NULL AND m.createdDate <= :toDate ))" +
            " AND ((:content IS NULL OR (:content IS NOT NULL AND (" +
            "                          LOWER(m.titleEnglish) LIKE CONCAT('%',LOWER(:content),'%')  OR " +
            "                          LOWER(m.contentEnglish) LIKE CONCAT('%',LOWER(:content),'%') ) )) OR " +
            "     ( m.newsID = :newId)) " +
            " AND m.status in (:status) " +
            " AND m.isDeleted = 0 " +
            " ORDER BY m.id desc ")
    Page<CMSMessageEntity> search(@Param("fromDate") Date fromDate,
                                  @Param("toDate") Date toDate,
                                  @Param("content") String content,
                                  @Param("newId") BigDecimal newId,
                                  @Param("status") List<Integer> status,
                                  Pageable pageable);

    @Query(value = " SELECT m FROM CMSMessageEntity m " +
            " WHERE m.newsID = :newId ")
    CMSMessageEntity findByNewsID(@Param("newId") BigDecimal newId);

    @Query(value = "select CMS_MESSAGE_SEQ.NEXTVAL from dual", nativeQuery = true)
    BigDecimal getSEQ();

}
