package com.bepay.application.services;

import com.bepay.application.controllers.AuthController;
import com.bepay.application.dto.response.UserResponse;
import com.bepay.application.models.auth.LoginRequest;

/**
 * @author DanTran
 * @return
 * @see AuthController
 */

public interface AuthService {
    public UserResponse login(LoginRequest loginRequest);
}
