package com.bepay.application.mapper;
import com.bepay.application.dto.response.MenusResponse;
import com.bepay.application.models.auth.Menus;
import org.springframework.stereotype.Component;

@Component
public interface MenuMapper extends AbstractMapper {
    MenusResponse convertToResponse(Menus menus);
}
