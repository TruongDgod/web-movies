package com.bepay.application.mapper;

import com.bepay.application.dto.response.UserResponse;
import com.bepay.application.models.auth.UserLoginInfo;
import org.springframework.stereotype.Component;

@Component
public interface UserLoginMapper extends AbstractMapper {
    UserResponse convertToResponse(UserLoginInfo user);
}
