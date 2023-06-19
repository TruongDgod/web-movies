package com.bepay.application.mapper.impl;

import com.bepay.application.dto.RoleDTO;
import com.bepay.application.entities.db.CMSRoleEntity;
import com.bepay.application.mapper.RoleMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl extends AbstractMapperImpl implements RoleMapper {
    @Override
    public RoleDTO convertDTO(CMSRoleEntity entity) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(entity.getId());
        roleDTO.setRole(entity.getName());
        return roleDTO;
    }
}
