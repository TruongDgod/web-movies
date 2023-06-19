package com.bepay.application.mapper.impl;


import com.bepay.application.dto.response.MenusResponse;
import com.bepay.application.mapper.MenuMapper;
import com.bepay.application.models.auth.Menus;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class MenuMapperImpl extends AbstractMapperImpl implements MenuMapper {

    @Override
    public MenusResponse convertToResponse(Menus menus) {
        MenusResponse response = new MenusResponse();
        response.setObjectId(menus.getObjectId());
        response.setObjectCode(menus.getObjectCode());
        response.setTitle(menus.getNameUS());
        response.setIcons(menus.getIcons());
        response.setObjectUrl(menus.getObjectUrl());
        if (CollectionUtils.isNotEmpty(menus.getListMenuChild()))
            response.setListMenuChild(menus.getListMenuChild().stream().map(item -> convertToResponse(item)).collect(Collectors.toList()));
        return response;
    }
}
