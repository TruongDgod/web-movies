package com.bepay.application.mapper.impl;

import com.bepay.application.mapper.AbstractMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class AbstractMapperImpl implements AbstractMapper {
    @Override
    public Object convertToDTO(Object dto, Object entity) {
        ModelMapper modelMapper = new ModelMapper();
        Object result = modelMapper.map(entity, dto.getClass());
        return result;
    }

    @Override
    public Object convertToEntity(Object dto, Object entity) {
        ModelMapper modelMapper = new ModelMapper();
        Object result = modelMapper.map(dto, entity.getClass());
        return result;
    }
    @Autowired
    MessageSource messageSource;

    public String getMessageSource(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }
}
