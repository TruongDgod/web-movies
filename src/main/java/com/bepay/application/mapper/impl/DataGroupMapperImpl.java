package com.bepay.application.mapper.impl;

import com.bepay.application.constant.AppConst;
import com.bepay.application.dto.DataGroupDTO;
import com.bepay.application.dto.ServiceGroupDTO;
import com.bepay.application.dto.response.DataGroupRespone;
import com.bepay.application.entities.db.DataGroupEntity;
import com.bepay.application.enums.RoleDataEnum;
import com.bepay.application.mapper.DataGroupMapper;
import com.bepay.application.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DataGroupMapperImpl extends AbstractMapperImpl implements DataGroupMapper {
    @Override
    public DataGroupRespone convertToRespone(DataGroupDTO dto) {
        DataGroupRespone respone = new DataGroupRespone();
        respone.setId(dto.getId());
        respone.setGroupName(StringUtils.convertUT8(dto.getName()));
        RoleDataEnum roleData = RoleDataEnum.getMap().get(dto.getRole().intValue());
        if(Objects.nonNull(roleData)) respone.setRole(roleData.getDesc());
        respone.setGroupCode(dto.getCode());
        if(dto.getStatus() == AppConst.STATUS_ON) {
            respone.setStatusMessage(AppConst.MESSAGE_STATUS_ON);
            respone.setStatus(true);
        }
        else {
            respone.setStatusMessage(AppConst.MESSAGE_STATUS_OFF);
            respone.setStatus(false);
        }
        return respone;
    }

    @Override
    public ServiceGroupDTO convertToDTO(DataGroupEntity entity) {
        ServiceGroupDTO dto = new ServiceGroupDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
