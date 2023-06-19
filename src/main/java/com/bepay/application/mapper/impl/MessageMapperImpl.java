package com.bepay.application.mapper.impl;

import com.bepay.application.constant.AppConst;
import com.bepay.application.dto.MessageDTO;
import com.bepay.application.entities.db.CMSMessageEntity;
import com.bepay.application.enums.*;
import com.bepay.application.mapper.MessageMapper;
import com.bepay.application.utils.DateUtil;
import com.bepay.application.utils.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Component
public class MessageMapperImpl extends AbstractMapperImpl implements MessageMapper {
    @Override
    public MessageDTO convertToDTO(CMSMessageEntity e) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(e.getId());
        messageDTO.setNewsId( e.getNewsID());
        messageDTO.setTitle(StringUtils.convertUT8(e.getTitleEnglish()));
        messageDTO.setContent(StringUtils.convertUT8(e.getContentEnglish()));
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getMap().get(e.getType());
        if(Objects.nonNull(categoryTypeEnum))
            messageDTO.setCategory(categoryTypeEnum.getType());
        MultiLanguageEnum multiLanguageEnum = MultiLanguageEnum.get(e.getIsMultiLanguage());
        if (Objects.nonNull(multiLanguageEnum)) {
            messageDTO.setLanguageStatus(multiLanguageEnum.getIsMulti());
            messageDTO.setLanguageStatusName(multiLanguageEnum.getDesc());
        }
        if(e.getPushedTime() == null)
            messageDTO.setPublishTime(DateUtil.convertDatetoString(e.getTimeSend(), AppConst.DATE_FORMAT_YYYYMMDD_HH_MM_SS));
        messageDTO.setCreateTime(DateUtil.convertDatetoString(e.getCreatedDate(), AppConst.DATE_FORMAT_YYYYMMDD_HH_MM_SS));
        messageDTO.setStatus(StatusNewsEnum.getStatus(e.getStatus()).getValue());
        return messageDTO;
    }

}
