package com.bepay.application.models.auth;

import lombok.*;

import java.util.List;

/**
 * @author DanTran
 * @return
 * @see UserLoginInfo
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginInfo {
    private UserInfo userInfo;
    private List<Roles> roles;
    private List<Menus> listMenuParent;
    private List<String> listMenuAccess;
    private List<Menus> listPermissionComponent;
    private Integer roleId;
}
