package com.bepay.application.repository.db;

import com.bepay.application.entities.db.AppImageEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


/**
 * @author
 * @see AppImageRepository
 */
@Repository
public interface AppImageRepository extends AbstractRepository<AppImageEntity, Long> {
    @Query(value = "select a  FROM AppImageEntity a " +
            "WHERE a.objId = :objId and a.imageType = :type and a.language = :lang")
    AppImageEntity getImageByObjId(BigDecimal objId, String type, String lang);

}
