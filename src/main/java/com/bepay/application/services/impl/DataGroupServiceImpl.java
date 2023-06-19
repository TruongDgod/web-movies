package com.bepay.application.services.impl;

import com.bepay.application.constant.AppConst;
import com.bepay.application.dto.*;
import com.bepay.application.dto.request.DataGroupRequestDTO;
import com.bepay.application.dto.request.DataGroupSearchRequest;
import com.bepay.application.dto.request.PromotionActionRequest;
import com.bepay.application.dto.request.SearchDetailRequest;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.DataGroupRespone;
import com.bepay.application.entities.db.AppConfigEntity;
import com.bepay.application.entities.db.DataGroupEntity;
import com.bepay.application.entities.db.DataPackageEntity;
import com.bepay.application.enums.AppTypeEnum;
import com.bepay.application.enums.RoleDataEnum;
import com.bepay.application.enums.StatusGroupDataEnum;
import com.bepay.application.exceptions.ParameterIllegalException;
import com.bepay.application.exceptions.SystemErrorException;
import com.bepay.application.mapper.DataGroupMapper;
import com.bepay.application.repository.db.AppConfigRepository;
import com.bepay.application.repository.db.DataGroupRepository;
import com.bepay.application.repository.db.DataPackageRespository;
import com.bepay.application.services.DataGroupService;
import com.bepay.application.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.bepay.application.config.MessageUtil.getMessage;

@Service
public class DataGroupServiceImpl extends AbstractServiceImpl implements DataGroupService {
    private static final Logger logger = LoggerFactory.getLogger(DataGroupServiceImpl.class);
    @Autowired
    private DataGroupRepository dataGroupRepository;
    @Autowired
    private AppConfigRepository appConfigRepository;
    @Autowired
    private DataGroupMapper dataGroupMapper;
    @Autowired
    private DataPackageRespository dataPackageRespository;

    @Override
    public BaseResponse search(DataGroupSearchRequest request) {
        logger.info("#DataGroupServiceImpl.search start... ");
        try {
            BaseResponse response = new BaseResponse();
            RoleDataEnum roleEnum = RoleDataEnum.getMap().get(request.getRole().intValue());
            StatusGroupDataEnum statusEnum = StatusGroupDataEnum.getMap().get(request.getStatus());
            if (Objects.isNull(roleEnum) || Objects.isNull(statusEnum)) {
                response.setMessage(getMessage("common.error.param"));
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                return response;
            }
            Pageable pageable = getPageable(request.getPage(), request.getSize());
            Page<DataGroupDTO> pageEntity = dataGroupRepository.search(
                    request.getName(),
                    roleEnum.getListSearch(),
                    statusEnum.getList(),
                    pageable);
            List<DataGroupRespone> listRes = pageEntity.getContent().stream().map(e ->
                    dataGroupMapper.convertToRespone(e)
            ).collect(Collectors.toList());
            return baseResponseSuccess(pageEntity, listRes);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.search : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }

    @Override
    public BaseResponse add(DataGroupRequestDTO request) {
        logger.info("#DataGroupServiceImpl.add start... ");
        try {
            BaseResponse response = new BaseResponse();
            DataActionDTO listRes = dataGroupRepository.searchPackage(request.getCode());
            if (Objects.nonNull(listRes)) {
                response.setMessage(getMessage("common.error.code.already"));
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                return response;
            }
            DataGroupEntity entity = new DataGroupEntity();
            dataGroupRepository.saveAndFlush(entity);
            entity = insertDataGroup(entity, request);
            dataGroupRepository.saveAndFlush(entity);
            saveAppConfig(AppConst.LANGUAGE_EN, request.getNameEn(), entity.getId());
            saveAppConfig(AppConst.LANGUAGE_FR, request.getNameFr(), entity.getId());
            saveAppConfig(AppConst.LANGUAGE_RN, request.getNameRn(), entity.getId());
            DataGroupRequestDTO dto = new DataGroupRequestDTO();
            dto = getDataResponse(entity, dto);
            response.setData(dto);
            response.setMessage(getMessage("common.insert.success"));
            response.setHttpStatus(HttpStatus.OK);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.add : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }

    @Override
    public DataGroupRequestDTO getDetail(SearchDetailRequest request) {
        logger.info("#DataGroupServiceImpl.detail start... ");
        try {
            DataGroupRequestDTO dto = new DataGroupRequestDTO();
            DataGroupEntity dataGroupEntity = dataGroupRepository.findById(request.getId()).orElse(null);
            if (Objects.isNull(dataGroupEntity)) {
                throw new ParameterIllegalException(getMessage("common.error.param"));
            }
            dto = getDataResponse(dataGroupEntity, dto);
            return dto;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.getDetail : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }

    @Override
    public void delete(SearchDetailRequest request) {
        logger.info("#DataGroupServiceImpl.delete start... ");
        try {
            DataGroupEntity entity = dataGroupRepository.findById(request.getId()).orElse(null);
            if (Objects.nonNull(entity)) {
                entity.setIsDeleted(AppConst.IS_DELETE_DISENABLE);
                entity.setStatus(AppConst.STATUS_OFF);
                dataGroupRepository.saveAndFlush(entity);
            }
            List<DataPackageEntity> dataPackageEntities = dataPackageRespository.getAllByGroupDataId(request.getId());
            dataPackageEntities.forEach(e -> e.setIsDeleted(1));
            dataPackageRespository.saveAllAndFlush(dataPackageEntities);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.delete : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }

    @Override
    public BaseResponse update(DataGroupRequestDTO request) {
        logger.info("#DataGroupServiceImpl.update start... ");
        try {
            BaseResponse response = new BaseResponse();
            DataActionDTO listRes = dataGroupRepository.searchPackage(request.getCode());
            if (Objects.nonNull(listRes) && !Objects.equals(listRes.getId(), request.getId())) {
                response.setMessage(getMessage("common.error.code.already"));
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                return response;
            }
            DataGroupEntity entity = dataGroupRepository.findById(request.getId()).orElse(null);
            if (Objects.isNull(entity)) {
                response.setMessage(getMessage("common.system.error"));
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                return response;
            }
            entity = insertDataGroup(entity, request);
            entity.setUpdateDate(new Date());
            dataGroupRepository.saveAndFlush(entity);

            updateAppConfig(AppConst.LANGUAGE_EN, request.getNameEn(), entity.getName());
            updateAppConfig(AppConst.LANGUAGE_FR, request.getNameFr(), entity.getName());
            updateAppConfig(AppConst.LANGUAGE_RN, request.getNameRn(), entity.getName());

            DataGroupRequestDTO dto = new DataGroupRequestDTO();
            dto = getDataResponse(entity, dto);
            response.setData(dto);
            response.setMessage(getMessage("common.update.success"));
            response.setHttpStatus(HttpStatus.OK);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.update:" + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }

    @Override
    public BaseResponse getStatus() {
        try {
            BaseResponse response = new BaseResponse();
            List<StatusSearch> statusSearchList = new ArrayList<>();
            List<Integer> lis = dataGroupRepository.getStatus();
            for (Integer i : lis) {
                StatusGroupDataEnum statusGroupDataEnum = StatusGroupDataEnum.getMap().get(i);
                StatusSearch dto = new StatusSearch();
                if (Objects.nonNull(statusGroupDataEnum)) {
                    dto.setId(i);
                    dto.setName(statusGroupDataEnum.getDesc());
                    statusSearchList.add(dto);
                }
            }
            response.setData(statusSearchList);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.update:" + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }
    @Override
    public BaseResponse getRole() {
        try {
            BaseResponse response = new BaseResponse();
            List<RoleDTO> roleList = new ArrayList<>();
            List<Integer> lis = dataGroupRepository.getStatus();
            for (Integer i : lis) {
                AppTypeEnum appEnum = AppTypeEnum.getMap().get(i);
                RoleDTO dto = new RoleDTO();
                if (Objects.nonNull(appEnum)) {
                    dto.setId(new BigDecimal(i));
                    dto.setRole(appEnum.getDesc());
                    roleList.add(dto);
                }
            }
            response.setData(roleList);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.update : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }
    @Override
    public BaseResponse searchPackage(PromotionActionRequest request) {
        try {
            BaseResponse response = new BaseResponse();
            response.setMessage(getMessage("common.ok"));
            DataActionDTO listRes = dataGroupRepository.searchPackage(request.getCode());
            response.setHttpStatus(HttpStatus.OK);
            response.setData(listRes);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.update : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }

    @Override
    public BaseResponse listAction() {
        try {
            BaseResponse response = new BaseResponse();
            response.setMessage(getMessage("common.ok"));
            List<DataActionDTO> listRes = dataGroupRepository.getListAction();
            response.setHttpStatus(HttpStatus.OK);
            response.setData(listRes);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.listAction : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }

    @Override
    public BaseResponse check(CodeDTO request) {
        BaseResponse response = new BaseResponse();
        List<DataGroupEntity> listRes = dataGroupRepository.getCode(request.getCode());
        if (CollectionUtils.isNotEmpty(listRes) && listRes.get(0) != null && !listRes.get(0).getId().equals(request.getId())) {
            response.setMessage(getMessage("common.error.code.already"));
            response.setData(listRes);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            return response;
        }
        response.setMessage(getMessage("common.ok"));
        response.setHttpStatus(HttpStatus.OK);
        return response;
    }


    private DataGroupRequestDTO getDataResponse(DataGroupEntity dataGroupEntity, DataGroupRequestDTO dto) {
        dto.setId(dataGroupEntity.getId());
        dto.setStatus(dataGroupEntity.getStatus() == AppConst.STATUS_ON);
        dto.setRole(dataGroupEntity.getRoleId());
        dto.setCode(dataGroupEntity.getCode());
        dto.setNameEn(StringUtils.convertUT8(getConfigValue(dataGroupEntity.getName(), AppConst.LANGUAGE_EN)));
        dto.setNameFr(StringUtils.convertUT8(getConfigValue(dataGroupEntity.getName(), AppConst.LANGUAGE_FR)));
        dto.setNameRn(StringUtils.convertUT8(getConfigValue(dataGroupEntity.getName(), AppConst.LANGUAGE_RN)));
        return dto;
    }

    private String getConfigValue(String configKey, String language) {
        AppConfigEntity nameEn = appConfigRepository.getByConfigKeyAndLanguage(configKey, language);
        if (Objects.nonNull(nameEn)) return nameEn.getConfigValue();
        else return null;
    }

    private DataGroupEntity insertDataGroup(DataGroupEntity entity, DataGroupRequestDTO request) {
        entity.setCode(request.getCode());
        RoleDataEnum roleDataEnum = RoleDataEnum.getMap().get(request.getRole().intValue());
        if (Objects.nonNull(roleDataEnum)) entity.setRoleId(roleDataEnum.getListSearch().get(0));
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        entity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        if (Boolean.TRUE.equals(request.getStatus())) entity.setStatus(AppConst.STATUS_ON);
        else entity.setStatus(AppConst.STATUS_OFF);
        entity.setName(AppConst.GROUP_DATA_NAME + entity.getId().toString());
        return entity;
    }

    private void saveAppConfig(String language, String value, BigDecimal objId) {
        AppConfigEntity appConfig = new AppConfigEntity();
        appConfig.setLanguage(language);
        appConfig.setFeatureCode(AppConst.ALL);
        appConfig.setObjType(AppConst.GROUP_DATA);
        appConfig.setObjId(objId);
        appConfig.setObjValue(AppConst.NAME);
        appConfig.setConfigKey(AppConst.GROUP_DATA_NAME + objId);
        appConfig.setConfigValue(StringUtils.convertWindows1252(value));
        appConfig.setCreateTime(new Date());
        appConfig.setUpdateTime(new Date());
        appConfigRepository.saveAndFlush(appConfig);
    }

    private void updateAppConfig(String language, String value, String configKey) {
        AppConfigEntity appConfig = appConfigRepository.findByObj(configKey, language);
        appConfig.setConfigValue(StringUtils.convertWindows1252(value));
        appConfig.setUpdateTime(new Date());
        appConfigRepository.saveAndFlush(appConfig);
    }

}
