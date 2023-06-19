package com.bepay.application.mapper.impl;

import com.bepay.application.constant.AppConst;
import com.bepay.application.dto.response.NewsResponse;
import com.bepay.application.entities.db.NewsEntity;
import com.bepay.application.enums.AppTypeEnum;
import com.bepay.application.enums.NewsTypeEnum;
import com.bepay.application.mapper.NewsMapper;
import com.bepay.application.utils.DateUtil;
import emoji4j.EmojiUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Component
public class NewsMapperImpl extends AbstractMapperImpl implements NewsMapper {
    @Override
    public NewsResponse convertEntityToResponse(NewsEntity entity, String title, String content) {
        NewsResponse response = new NewsResponse();
        response.setId(entity.getId());
        if (AppConst.STATUS_ON == entity.getStatus())response.setStatus(AppConst.MESSAGE_STATUS_ON);
            else response.setStatus(AppConst.MESSAGE_STATUS_OFF);
        response.setCreateTime(DateUtil.convertDatetoString(entity.getCreateTime(), AppConst.DATE_FORMAT_YYYYMMDD_HH_MM_SS));
        response.setTitle(title);
        response.setContent(content);
        AppTypeEnum typeEnum = AppTypeEnum.getMapCode().get(entity.getClientType());
        if(Objects.nonNull(typeEnum))
            response.setRole(typeEnum.getDesc());
        NewsTypeEnum newsTypeEnum = NewsTypeEnum.get(entity.getNewsType());
        if(Objects.nonNull(newsTypeEnum))
            response.setType(newsTypeEnum.getDesc());
        if(entity.getMultiLanguage() != null){
            if(AppConst.INT_MULTI_LANGUAGE == entity.getMultiLanguage()) {
                response.setMultiLanguageMessage(AppConst.MESSAGE_STATUS_ON);
                response.setMultiLanguage(true);
            }
            else {
                response.setMultiLanguageMessage(AppConst.MESSAGE_STATUS_OFF);
                response.setMultiLanguage(false);
            }
        }
        if( Objects.nonNull(entity.getShowOnTop()) && AppConst.STATUS_ON == entity.getShowOnTop()) response.setShowOnTop(AppConst.MESSAGE_STATUS_ON);
            else response.setShowOnTop(AppConst.MESSAGE_STATUS_OFF);
        if(Objects.nonNull(entity.getShowAtHome()) && AppConst.STATUS_ON == entity.getShowAtHome()) response.setShowAtHome(AppConst.MESSAGE_STATUS_ON);
            else response.setShowAtHome(AppConst.MESSAGE_STATUS_OFF);
        if(Objects.nonNull(entity.getHotNews()) &&AppConst.STATUS_ON == entity.getHotNews()) response.setHotNews(AppConst.MESSAGE_STATUS_ON);
            else response.setHotNews(AppConst.MESSAGE_STATUS_OFF);
        return response;
    }

}
