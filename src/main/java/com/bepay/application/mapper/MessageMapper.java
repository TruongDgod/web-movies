package com.bepay.application.mapper;

import com.bepay.application.dto.MessageDTO;
import com.bepay.application.entities.db.CMSMessageEntity;
import org.springframework.stereotype.Component;

@Component
public interface MessageMapper {
    MessageDTO convertToDTO(CMSMessageEntity entity);
}
