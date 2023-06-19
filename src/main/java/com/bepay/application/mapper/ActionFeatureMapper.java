package com.bepay.application.mapper;

import com.bepay.application.dto.DataActionDTO;
import com.bepay.application.entities.db.ActionFeatureEntity;
import org.springframework.stereotype.Component;

@Component
public interface ActionFeatureMapper extends AbstractMapper{
    public DataActionDTO convertToDTO(ActionFeatureEntity entity);
}
