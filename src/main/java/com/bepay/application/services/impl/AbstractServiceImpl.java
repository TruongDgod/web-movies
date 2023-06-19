package com.bepay.application.services.impl;

import com.bepay.application.config.LocaleConfig;
import com.bepay.application.constant.AppConst;
import com.bepay.application.dto.AbstractDTO;
import com.bepay.application.dto.paging.Paging;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.entities.AbstractEntity;
import com.bepay.application.services.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.Locale;

public class AbstractServiceImpl<D extends AbstractDTO, E extends AbstractEntity> implements AbstractService<D, E> {
    @Autowired
    MessageSource messageSource;

    /**
     * get Pageable object with sortable
     *
     * @param page {@link Integer}
     * @param size {@link Integer}
     * @return Pageable
     */
    @Override
    public Pageable getPageable(Integer page, Integer size, boolean sortASC, String by) {
        Sort sortable;
        if (StringUtils.isNotBlank(by)) {
            if (sortASC) {
                sortable = Sort.by(by).ascending();
            } else {
                sortable = Sort.by(by).descending();
            }
            return PageRequest.of(page, size, sortable);
        }
        return PageRequest.of(page, size);
    }

    @Override
    public Pageable getPageable(Integer page, Integer size, Sort sort) {
        if (sort != null) {
            return PageRequest.of(page, size, sort);
        }
        return PageRequest.of(page, size);
    }

    /**
     * get Pageable object
     *
     * @param page {@link Integer}
     * @param size {@link Integer}
     * @return Pageable
     */
    @Override
    public Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(page, size);
    }

    /**
     * getMessageSource
     *
     * @param key {@link String}
     * @return String
     */
    @Override
    public String getMessageSource(String key) {
        return messageSource.getMessage(key, null, LocaleConfig.getInstance().getLocale());
    }

    @Override
    public String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }

    /**
     * @param page pageable
     * @param data data response
     * @return BaseResponse
     */
    @Override
    public BaseResponse baseResponseSuccess(Page<Object> page, Object data) {
        BaseResponse baseResponse = new BaseResponse();
        Paging paging = new Paging();
        baseResponse.setMessage(getMessageSource("common.success"));
        baseResponse.setHttpStatus(HttpStatus.OK);
        paging.setPage(page.getPageable().getPageNumber());
        paging.setSize(page.getPageable().getPageSize());
        paging.setTotalPages(page.getTotalPages());
        baseResponse.setPaging(paging);
        baseResponse.setData(data);
        return baseResponse;
    }
}
