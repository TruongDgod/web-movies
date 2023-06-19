package com.bepay.application.repository.db;

import com.bepay.application.entities.db.ActionFeatureEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ActionRepository extends AbstractRepository<ActionFeatureEntity, BigDecimal> {
}
