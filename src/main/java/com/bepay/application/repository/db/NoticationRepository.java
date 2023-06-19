package com.bepay.application.repository.db;

import com.bepay.application.entities.db.FeatureCodeEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticationRepository extends AbstractRepository<FeatureCodeEntity, Long> {
}
