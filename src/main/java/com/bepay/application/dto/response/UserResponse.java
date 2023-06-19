package com.bepay.application.dto.response;

import com.bepay.application.models.auth.Menus;
import com.bepay.application.models.auth.Roles;
import com.bepay.application.models.auth.UserInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private Long userId;

    private String username;

    private String roleCode;

    private String roleName;

    private List<MenusResponse> listMenu;



}