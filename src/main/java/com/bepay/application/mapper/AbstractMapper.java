package com.bepay.application.mapper;
/**
 * @see AbstractMapper
 *
 * @author vidal
 * @return
 */
public interface AbstractMapper {
    Object convertToDTO(Object dto, Object entity);
    Object convertToEntity(Object dto, Object entity);
}
