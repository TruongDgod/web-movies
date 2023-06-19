package com.bepay.application.mapper;

import com.bepay.application.dto.RoleDTO;
import com.bepay.application.entities.db.CMSRoleEntity;
import org.springframework.stereotype.Component;

@Component
public interface RoleMapper extends AbstractMapper {
    public RoleDTO convertDTO(CMSRoleEntity entity);
}
