package com.bepay.application.repository.db;

import com.bepay.application.entities.db.FeatureCodeEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @see FeatureCodeRepository
 */
@Repository
public interface FeatureCodeRepository extends AbstractRepository<FeatureCodeEntity, Long> {
    @Query(value = "select f  FROM FeatureCodeEntity f " +
            "WHERE f.status = 1 ")
    List<FeatureCodeEntity> getAll();
}
