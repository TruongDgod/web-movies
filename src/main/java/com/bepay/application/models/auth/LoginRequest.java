package com.bepay.application.models.auth;


import com.bepay.application.dto.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest extends BaseRequest {
    private String username;
    private String password;
}
