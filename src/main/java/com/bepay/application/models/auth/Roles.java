package com.bepay.application.models.auth;

//import com.amls.application.controllers.AuthController;
import lombok.Getter;
import lombok.Setter;

/**
 * @author DanTran
 * @return
// * @see AuthController
 */

@Getter
@Setter
public class Roles {
    private int status;
    private int roleId;
    private String roleName;
    private String description;
    private String roleCode;
    private String createDate;
    private int creatorId;
    private String creatorName;
    private int ipOfficeWan;

    @Override
    public String toString() {
        return "Roles{" +
                "status=" + status +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", createDate='" + createDate + '\'' +
                ", creatorId=" + creatorId +
                ", creatorName='" + creatorName + '\'' +
                ", ipOfficeWan=" + ipOfficeWan +
                '}';
    }
}
