package com.bepay.application.mapper.impl;

import com.bepay.application.dto.DataActionDTO;
import com.bepay.application.entities.db.ActionFeatureEntity;
import com.bepay.application.mapper.ActionFeatureMapper;
import org.springframework.stereotype.Component;

@Component
public class ActionFeatureMapperImpl extends AbstractMapperImpl implements ActionFeatureMapper {
    @Override
    public DataActionDTO convertToDTO(ActionFeatureEntity entity) {
        DataActionDTO dto = new DataActionDTO();
        dto.setName(entity.getTilte());
        dto.setCode(entity.getValue());
        dto.setId(entity.getId());
        return dto;
    }
}
