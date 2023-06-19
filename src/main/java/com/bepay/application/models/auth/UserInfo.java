package com.bepay.application.models.auth;
import lombok.Getter;
import lombok.Setter;



/**
 * @author DanTran
 * @return
 * @see UserInfo
 */

@Getter
@Setter
public class UserInfo {
    private Long userId;
    private int userRight;
    private String username;
    private String password;
    private int status;
    private String email;
    private String cellPhone;
    private int gender;
    private String identityCard;
    private String fullName;
    private String userTypeId;
    private String createDate;
    private int managerId;
    private int passWordChange;
    private String profileId;
    private String lastResetPassword;
    private String ip;
    private int deptId;
    private String deptLevel;
    private int postId;
    private String deptName;
    private int ignoreCheckIp;
    private int checkValidTime;
    private String startTimeToChangePassword;
    private String ipLan;
    private int checkIp;
    private int checkIpLan;
    private int useSalt;
    private int loginFailAllow;
    private int temporaryLockTime;
    private int maxTmpLockADay;
    private int passwordValidTime;
    private int userValidTime;
    private int allowMultiIpLogin;
    private int allowLoginTimeStart;
    private int allowLoginTimeEnd;
    private int id;
    private String name;
    private int needChangePassword;
    private int timeToChangePassword;

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userRight=" + userRight +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", gender=" + gender +
                ", identityCard='" + identityCard + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userTypeId='" + userTypeId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", managerId=" + managerId +
                ", passWordChange=" + passWordChange +
                ", profileId='" + profileId + '\'' +
                ", lastResetPassword='" + lastResetPassword + '\'' +
                ", ip='" + ip + '\'' +
                ", deptId=" + deptId +
                ", deptLevel='" + deptLevel + '\'' +
                ", postId=" + postId +
                ", deptName='" + deptName + '\'' +
                ", ignoreCheckIp=" + ignoreCheckIp +
                ", checkValidTime=" + checkValidTime +
                ", startTimeToChangePassword='" + startTimeToChangePassword + '\'' +
                ", ipLan='" + ipLan + '\'' +
                ", checkIp=" + checkIp +
                ", checkIpLan=" + checkIpLan +
                ", useSalt=" + useSalt +
                ", loginFailAllow=" + loginFailAllow +
                ", temporaryLockTime=" + temporaryLockTime +
                ", maxTmpLockADay=" + maxTmpLockADay +
                ", passwordValidTime=" + passwordValidTime +
                ", userValidTime=" + userValidTime +
                ", allowMultiIpLogin=" + allowMultiIpLogin +
                ", allowLoginTimeStart=" + allowLoginTimeStart +
                ", allowLoginTimeEnd=" + allowLoginTimeEnd +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", needChangePassword=" + needChangePassword +
                ", timeToChangePassword=" + timeToChangePassword +
                '}';
    }
}
