package com.bepay.application.models.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DanTran
 * @return
 * @see Menus
 */

@Getter
@Setter
@ToString
public class Menus implements Comparable<Menus> {
    private Long objectId;
    private int appId;
    private int status;
    private String objectName;
    private int objectTypeId;
    private String objectCode;
    private String objectLevel;
    private String createDate;
    private long objectType;
    private long parentId;
    private Long ord;
    private String nameUS;
    private String nameVN;
    private String icons;
    private String objectUrl;
    private List<Menus> listMenuChild = new ArrayList();
    private List<Menus> listMenuComponent = new ArrayList();

    @Override
    public int compareTo(Menus permission) {
        return this.objectId.compareTo(permission.getObjectId());
    }
}
