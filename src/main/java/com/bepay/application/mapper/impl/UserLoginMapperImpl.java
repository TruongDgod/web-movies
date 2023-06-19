package com.bepay.application.mapper.impl;


import com.bepay.application.dto.response.UserResponse;
import com.bepay.application.mapper.MenuMapper;
import com.bepay.application.mapper.UserLoginMapper;
import com.bepay.application.models.auth.UserLoginInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class UserLoginMapperImpl extends AbstractMapperImpl implements UserLoginMapper {

    @Autowired
    MenuMapper menuMappper;

    @Override
    public UserResponse convertToResponse(UserLoginInfo user) {
        if (Objects.isNull(user) || Objects.isNull(user.getUserInfo()) || Objects.isNull(user.getRoles())) return null;
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserInfo().getUserId());
        response.setUsername(user.getUserInfo().getUsername());
        response.setRoleCode(user.getRoles().get(0).getRoleCode());
        response.setRoleName(user.getRoles().get(0).getRoleName());
        if (CollectionUtils.isNotEmpty(user.getListMenuParent()))
            response.setListMenu(user.getListMenuParent().stream().map(item -> menuMappper.convertToResponse(item)).collect(Collectors.toList()));

        return response;
    }
}
