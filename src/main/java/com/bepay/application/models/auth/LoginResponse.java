package com.bepay.application.models.auth;

import com.bepay.application.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends BaseResponse {
    private String accessToken;
}
