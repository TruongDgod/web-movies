package com.bepay.application.repository.db;

import com.bepay.application.entities.db.NewsEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author
 * @see NewsRepository
 */
@Repository
public interface NewsRepository extends AbstractRepository<NewsEntity, Long> {
    @Query(value = "SELECT e FROM NewsEntity e where  e.newsType ='NEWS' ")
    Page<NewsEntity> findAll(Pageable pageable);

    @Query(value = "SELECT e FROM NewsEntity e where e.id = :id ")
    NewsEntity getById(BigDecimal id);


    @Query(value = " SELECT DISTINCT e FROM NewsEntity e " +
            " INNER JOIN AppConfigEntity ac ON e.id = ac.objId " +
            " WHERE ac.objType IN ('PROMOTION', 'NEWS') " +
            " AND ac.objValue  IN ('TITLE', 'SHORT_CONTENT')" +
            " AND ac.language = 'en'" +
            " AND (:fromDate IS NULL OR (:fromDate IS NOT NULL AND e.createTime >= :fromDate ))" +
            " AND (:toDate IS NULL OR (:toDate IS NOT NULL AND e.createTime <= :toDate ))" +
            " AND e.status in (:status) " +
            " AND (:content IS NULL OR( :content IS NOT NULL AND (LOWER(ac.configValue) LIKE CONCAT('%',LOWER(:content),'%'))))" +
            " AND e.isDeleted = 0" +
            " ORDER BY e.id desc ")
    Page<NewsEntity> search(Pageable pageable,
                            Date fromDate,
                            Date toDate,
                            List<Integer> status,
                            String content);

}
