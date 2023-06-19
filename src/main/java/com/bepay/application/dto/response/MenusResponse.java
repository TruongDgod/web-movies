package com.bepay.application.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DanTran
 * @return
 * @see MenusResponse
 */

@Getter
@Setter
@ToString
public class MenusResponse implements Comparable<MenusResponse> {
    private Long objectId;
    private String objectCode;

    private String title;

    private String icons;

    private String objectUrl;

    private List<MenusResponse> listMenuChild = new ArrayList();

    private List<MenusResponse> listMenuComponent = new ArrayList();


    @Override
    public int compareTo(MenusResponse permission) {
        return this.objectId.compareTo(permission.getObjectId());
    }
}
