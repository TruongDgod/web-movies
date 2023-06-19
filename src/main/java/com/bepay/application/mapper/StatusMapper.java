package com.bepay.application.mapper;

import com.bepay.application.dto.StatusSearch;

public interface StatusMapper extends AbstractMapper{
    public StatusSearch convertToDTO(Integer id);
}
