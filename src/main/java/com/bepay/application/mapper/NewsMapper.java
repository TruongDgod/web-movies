package com.bepay.application.mapper;

import com.bepay.application.dto.response.NewsResponse;
import com.bepay.application.entities.db.NewsEntity;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public interface NewsMapper extends AbstractMapper {
    NewsResponse convertEntityToResponse(NewsEntity entity, String title, String content) throws UnsupportedEncodingException;
}
