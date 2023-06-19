package com.bepay.application.services;

import com.bepay.application.dto.AbstractDTO;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.entities.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * AbstractService
 *
 * @author vidal
 * @return
 */
public interface AbstractService<D extends AbstractDTO, E extends AbstractEntity> {
    /**
     * get Pageable object with sortable
     *
     * @param page {@link Integer}
     * @param size {@link Integer}
     * @return pageable {@link Pageable}
     */
    Pageable getPageable(Integer page, Integer size, boolean sortASC, String by);

    Pageable getPageable(Integer page, Integer size, Sort sort);

    /**
     * get Pageable object
     *
     * @param page {@link Integer}
     * @param size {@link Integer}
     * @return pageable {@link Pageable}
     */
    Pageable getPageable(Integer page, Integer size);

    /**
     * getMessageSource
     *
     * @param key {@link String}
     * @return pageable {@link Pageable}
     */
    String getMessageSource(String key);

    String getLanguage();

    BaseResponse baseResponseSuccess(Page<Object> page, Object data);
}
