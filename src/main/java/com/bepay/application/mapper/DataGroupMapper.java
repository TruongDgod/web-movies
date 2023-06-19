package com.bepay.application.mapper;

import com.bepay.application.dto.DataGroupDTO;
import com.bepay.application.dto.ServiceGroupDTO;
import com.bepay.application.dto.response.DataGroupRespone;
import com.bepay.application.entities.db.DataGroupEntity;
import org.springframework.stereotype.Component;

@Component
public interface DataGroupMapper extends AbstractMapper{
    DataGroupRespone convertToRespone(DataGroupDTO dto);
    ServiceGroupDTO convertToDTO(DataGroupEntity entity);
}
