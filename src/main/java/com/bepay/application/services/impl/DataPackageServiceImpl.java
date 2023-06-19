package com.bepay.application.services.impl;

import com.bepay.application.constant.AppConst;
import com.bepay.application.dto.*;
import com.bepay.application.dto.request.*;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.DataPackageResponeDTO;
import com.bepay.application.dto.response.DataPackageResponseDetail;
import com.bepay.application.entities.db.*;
import com.bepay.application.enums.RoleDataEnum;
import com.bepay.application.enums.StatusGroupDataEnum;
import com.bepay.application.exceptions.ParameterIllegalException;
import com.bepay.application.exceptions.SystemErrorException;
import com.bepay.application.mapper.RoleMapper;
import com.bepay.application.repository.db.*;
import com.bepay.application.services.DataPackageService;
import com.bepay.application.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.bepay.application.config.MessageUtil.getMessage;

@Service
public class DataPackageServiceImpl extends AbstractServiceImpl implements DataPackageService {
    private static final Logger logger = LoggerFactory.getLogger(DataPackageServiceImpl.class);
    @Autowired
    private DataPackageRespository dataPackageRespository;
    @Autowired
    private AppConfigRepository appConfigRepository;
    @Autowired
    private DataGroupRepository dataGroupRepository;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public BaseResponse search(DataPackageRequest request) {
        logger.info("#DataPackageServiceImpl.search    Start..... ");
        try {
            StatusGroupDataEnum status = StatusGroupDataEnum.getMap().get(request.getStatus());
            List<DataGroupEntity> groupDatas = dataGroupRepository.getName(request.getGroupName());
            Set<BigDecimal> groupDataIds = new HashSet<>();
            groupDatas.forEach(e->groupDataIds.add(e.getId()));
            Pageable pageable = getPageable(request.getPage(), request.getSize());

            Page<DataPackageDTO> pageData = dataPackageRespository.search(
                    request.getName(),
                    status.getList(),
                    groupDataIds,
                    RoleDataEnum.getDefault(request.getRole()).getListSearch(),
                    request.getCode(),
                    pageable);
            List<DataPackageResponeDTO> listRes = pageData.getContent().stream().map(e -> convert(e, AppConst.LANGUAGE_EN)).collect(Collectors.toList());
            return baseResponseSuccess(pageData, listRes);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataPackageServiceImpl.search " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }
    @Override
    public List<RoleDTO> getRole(BigDecimal groupId) {
        logger.info("#DataPackageServiceImpl.getRole    Start..... ");
        try {
            List<CMSRoleEntity> roleEntityList = dataGroupRepository.getRole(groupId);
            return roleEntityList.stream().map(e -> roleMapper.convertDTO(e)).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataPackageServiceImpl.getRole " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }
    @Override
    public List<ServiceGroupDTO> getGroup(DataPackageGetRoleRequest request) {
        logger.info("#DataPackageServiceImpl.getGroup   Start..... ");
        try {
            return dataGroupRepository.getGroup(request.getRoleId());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataPackageServiceImpl.getGroup " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }
    @Override
    public BaseResponse add(DataPackageAddRequest request) {
        logger.info("#DataPackageServiceImpl.add   Start..... ");
        try {
            BaseResponse reponse = new BaseResponse();

            DataPackageActionDTO dtoCheck = dataPackageRespository.getDataPackageAction(request.getCode());
            if(Objects.nonNull(dtoCheck) && !dtoCheck.getId().equals(request.getId())){
                reponse.setMessage(getMessageSource("common.error.code.already"));
                reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
                return reponse;
            }
            DataPackageEntity dataPackageEntity = new DataPackageEntity();
            dataPackageEntity = insertDataPackage(dataPackageEntity,request);
            dataPackageRespository.saveAndFlush(dataPackageEntity);

            insertNameAndTitle(request,dataPackageEntity.getShortName(),dataPackageEntity.getId(),AppConst.GROUP_DATA_PACKAGE_SHORT_NAME_STR);
            insertNameAndTitle(request,dataPackageEntity.getTitle(),dataPackageEntity.getId(),AppConst.TITLE);
            insertSub(request,dataPackageEntity.getSubTitle(),dataPackageEntity.getId(),AppConst.SUB_TITLE);
            insertDesc(request,dataPackageEntity.getDescription(),dataPackageEntity.getId(),AppConst.DESCRIPTION);

            DataPackageResponseDetail data =  getDataResponse(dataPackageEntity);
            reponse.setData(data);
            reponse.setMessage(getMessageSource("common.insert.success"));
            reponse.setHttpStatus(HttpStatus.OK);
            return reponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataPackageServiceImpl.add " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }
    @Override
    public DataPackageResponseDetail detail(SearchDetailRequest request) {
        logger.info("#DataPackageServiceImpl.detail   Start..... ");
        try {
            DataPackageEntity dataPackageEntity = dataPackageRespository.findById(request.getId()).orElse(null);
            if(Objects.isNull(dataPackageEntity)){
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            }
            return getDataResponse(dataPackageEntity);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataPackageServiceImpl.detail " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    @Override
    public BaseResponse update(DataPackageAddRequest request) {
        logger.info("#DataPackageServiceImpl.update   Start..... ");
        try {
            BaseResponse response = new BaseResponse();
            DataPackageActionDTO dtoCheck = dataPackageRespository.getDataPackageAction(request.getCode());
            if(Objects.nonNull(dtoCheck) && !Objects.equals(dtoCheck.getId(), request.getId())){
                response.setMessage(getMessageSource("common.error.code.already"));
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                return response;
            }
            DataPackageEntity dataPackageEntity = dataPackageRespository.findById(request.getId()).orElse(null);
            if(Objects.isNull(dataPackageEntity)){
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            }
            dataPackageEntity = insertDataPackage(dataPackageEntity,request);
            dataPackageEntity.setUpdateDate(new Date());
            dataPackageRespository.saveAndFlush(dataPackageEntity);

            insertNameAndTitle(request,dataPackageEntity.getShortName(),dataPackageEntity.getId(),AppConst.GROUP_DATA_PACKAGE_SHORT_NAME_STR);
            insertNameAndTitle(request,dataPackageEntity.getTitle(),dataPackageEntity.getId(),AppConst.TITLE);
            insertSub(request,dataPackageEntity.getSubTitle(),dataPackageEntity.getId(),AppConst.SUB_TITLE);
            insertDesc(request,dataPackageEntity.getDescription(),dataPackageEntity.getId(),AppConst.DESCRIPTION);

            DataPackageResponseDetail data = getDataResponse(dataPackageEntity);
            response.setData(data);
            response.setMessage(getMessageSource("common.update.success"));
            response.setHttpStatus(HttpStatus.OK);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataPackageServiceImpl.update " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }
    @Override
    public void delete(SearchDetailRequest request) {
        logger.info("#DataPackageServiceImpl.delete   Start..... ");
        try {
            DataPackageEntity dataPackageEntity = dataPackageRespository.findById(request.getId()).orElse(null);
            if(Objects.isNull(dataPackageEntity)){
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            }
            dataPackageEntity.setIsDeleted(AppConst.IS_DELETE_DISENABLE);
            dataPackageEntity.setStatus(AppConst.STATUS_OFF);
            dataPackageRespository.saveAndFlush(dataPackageEntity);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataPackageServiceImpl.delete " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }
    @Override
    public BaseResponse getStatus() {
        try {
            BaseResponse response = new BaseResponse();
            List<StatusSearch> statusSearchList = new ArrayList<>();
            List<Integer> listStatus = dataGroupRepository.getStatus();
            for(Integer i : listStatus){
                StatusSearch statusSearch = new StatusSearch();
                statusSearch.setId(i);
                StatusGroupDataEnum statusEnum = StatusGroupDataEnum.getMap().get(i);
                statusSearch.setName(statusEnum.getDesc());
                statusSearchList.add(statusSearch);
            }
            response.setData(statusSearchList);
            return response;
        } catch (Exception ex){
            ex.printStackTrace();
            logger.error("#DataPackageServiceImpl.delete " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }
    @Override
    public BaseResponse searchPackage(DataPackageActionRequest request) {
        logger.info("#DataPackageServiceImpl.searchPackage    Start..... ");
        try {
            List<DataPackageActionDTO> listRes = dataPackageRespository.searchDataGroup(
                    request.getId());
            BaseResponse response = new BaseResponse();
            response.setMessage(getMessageSource("common.ok"));
            response.setHttpStatus(HttpStatus.OK);
            response.setData(listRes);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataPackageServiceImpl.search " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }
    @Override
    public BaseResponse detailAction(PromotionActionRequest request) {
        try {
            BaseResponse response = new BaseResponse();
            response.setMessage(getMessage("common.ok"));
            DataPackageActionDTO listRes = dataPackageRespository.getDataPackageAction(request.getCode());
            response.setHttpStatus(HttpStatus.OK);
            response.setData(listRes);
            return response;
        } catch (Exception ex){
            ex.printStackTrace();
            logger.error("#DataGroupServiceImpl.update : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessage("common.system.error"));
        }
    }
    @Override
    public BaseResponse check(CodeDTO request) {
        BaseResponse response = new BaseResponse();
        DataPackageEntity dtoCheck = dataPackageRespository.getCode(request.getCode());
        if(Objects.nonNull(dtoCheck)  && !dtoCheck.getId().equals(request.getId())){
            response.setMessage(getMessage("common.error.code.already"));
            response.setData(dtoCheck);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            return response;
        }
        response.setMessage(getMessage("common.ok"));
        response.setHttpStatus(HttpStatus.OK);
        return response;
    }

    private void saveAppConfig(AppConfigEntity appConfig, String value, String language, String configKey, BigDecimal objId, String objValue) {
        appConfig.setLanguage(language);
        appConfig.setFeatureCode(AppConst.ALL);
        appConfig.setObjType(AppConst.GROUP_DATA_PACKAGE);
        appConfig.setObjId(objId);
        appConfig.setObjValue(objValue);
        appConfig.setConfigKey(configKey);
        appConfig.setConfigValue(StringUtils.convertWindows1252(value));
        appConfig.setStatus(AppConst.STATUS_ON);
        appConfig.setCreateTime(new Date());
        appConfig.setUpdateTime(new Date());
        appConfigRepository.saveAndFlush(appConfig);
    }
    private DataPackageResponeDTO convert(DataPackageDTO e, String lang) {
        DataPackageResponeDTO dto = new DataPackageResponeDTO();
        dto.setId(e.getId());
        if (Objects.nonNull(e.getName())) dto.setName(StringUtils.convertUT8(e.getName()));
        if (Objects.nonNull(e.getCode())) dto.setCode(StringUtils.convertUT8(e.getCode()));
        RoleDataEnum roleEnum = RoleDataEnum.getMap().get(e.getRole().intValue());
        if (Objects.nonNull(roleEnum)){
            dto.setRole(roleEnum.getDesc());
        }
        if (Objects.nonNull(e.getGroupName())) {
            String groupName = null;
            AppConfigEntity configEntity = appConfigRepository.getByConfigKeyAndLanguage(e.getGroupName(), lang);
            if (Objects.nonNull(configEntity)) groupName = configEntity.getConfigValue();
            dto.setGroupName(StringUtils.convertUT8(groupName));
        }
        if (Objects.nonNull(e.getStatus())) {
            if (e.getStatus() == AppConst.STATUS_ON){
                dto.setStatusMessage(AppConst.MESSAGE_STATUS_ON);
                dto.setStatus(true);
            } else {
                dto.setStatusMessage(AppConst.MESSAGE_STATUS_OFF);
                dto.setStatus(false);
            }
        }
        return dto;
    }
    private String saveImage( BigDecimal objId, ImageDTO image) throws IOException {
        Path currentRelativePath = Paths.get("");
//        String fileName = StringUtil.subNameFile(image.getName());
        String fileName = StringUtils.subNameFile(UUID.randomUUID().toString() + objId + ".png");
        String uploadDir = currentRelativePath.toAbsolutePath() + "/../assets"+ AppConst.URL_IMAGE_DATA_PACKAGE + objId;
        Path uploadPath = Paths.get(uploadDir, fileName).toAbsolutePath().normalize();
        String encodedImg = image.getFile().split(",")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));
        FileUtils.writeByteArrayToFile(new File(uploadPath.toString()), decodedBytes);
        return (AppConst.URL_IMAGE_DATA_PACKAGE  +objId + "/" + fileName);
    }
    private ImageDTO getImage(String url) throws IOException {
        try {
            ImageDTO image = new ImageDTO();
            Path currentRelativePath = Paths.get("").toAbsolutePath().normalize();
            File file = new File(currentRelativePath +"/../assets"+ url);
            byte[] bytes = FileUtils.readFileToByteArray(file);
            image.setFile("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bytes));
            String imageName = url.replace(AppConst.URL_IMAGE, "");
            int index = imageName.lastIndexOf("/") + 1;
            image.setName(imageName.substring(index, imageName.length()));
            image.setStatus(true);
            return image;
        } catch (Exception ex) {
            return new ImageDTO(null, null, false);
        }
    }
    private DataPackageEntity insertDataPackage(DataPackageEntity dataPackageEntity, DataPackageAddRequest request) throws IOException {
        dataPackageRespository.saveAndFlush(dataPackageEntity);
        dataPackageEntity.setCode(request.getCode());
        dataPackageEntity.setGroupDataId(request.getServiceGroup());
        dataPackageEntity.setStatus(AppConst.STATUS_OFF);
        if(Boolean.TRUE.equals(request.getStatus())) dataPackageEntity.setStatus(AppConst.STATUS_ON);
        dataPackageEntity.setRoleId(request.getRole());
        dataPackageEntity.setValue(request.getValue());
        dataPackageEntity.setCreateDate(new Date());
        dataPackageEntity.setUpdateDate(new Date());
        dataPackageEntity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        dataPackageEntity.setShortName(AppConst.GROUP_DATA_PACKAGE_SHORT_NAME + dataPackageEntity.getId());
        dataPackageEntity.setTitle(AppConst.GROUP_DATA_PACKAGE_TITLE + dataPackageEntity.getId());
        if (Objects.nonNull(request.getSubEn()) || Objects.nonNull(request.getSubFr()) || Objects.nonNull(request.getSubRn())) {
            dataPackageEntity.setSubTitle(AppConst.GROUP_DATA_PACKAGE_SUB_TITLE + dataPackageEntity.getId());
        }
        if (Objects.nonNull(request.getDescEn()) || Objects.nonNull(request.getDescFr()) || Objects.nonNull(request.getDescRn())) {
            dataPackageEntity.setDescription(AppConst.GROUP_DATA_PACKAGE_DESCRIPTION + dataPackageEntity.getId());
        }
        if(Objects.nonNull(request.getImage()) && Objects.nonNull(request.getImage().getFile())){
            dataPackageEntity.setUrl(saveImage(dataPackageEntity.getId(), request.getImage()));
        }
        return dataPackageEntity;
    }
    private DataPackageResponseDetail getDataResponse(DataPackageEntity dataPackageEntity) throws IOException {
        DataPackageResponseDetail response = new DataPackageResponseDetail();
        ServiceGroupDTO groupServiceDTO = dataGroupRepository.getServiceGroupDetail(dataPackageEntity.getGroupDataId());

        response.setId(dataPackageEntity.getId());
        response.setCode(dataPackageEntity.getCode());
        response.setServiceGroup(groupServiceDTO);
        response.setValue(dataPackageEntity.getValue());
        response.setRole(dataPackageEntity.getRoleId());

        response.setNameEn(getConfigValue(dataPackageEntity.getShortName(), AppConst.LANGUAGE_EN));
        response.setNameFr(getConfigValue(dataPackageEntity.getShortName(), AppConst.LANGUAGE_FR));
        response.setNameRn(getConfigValue(dataPackageEntity.getShortName(), AppConst.LANGUAGE_RN));

        response.setSubEn(getConfigValue(dataPackageEntity.getSubTitle(), AppConst.LANGUAGE_EN));
        response.setSubFr(getConfigValue(dataPackageEntity.getSubTitle(), AppConst.LANGUAGE_FR));
        response.setSubRn(getConfigValue(dataPackageEntity.getSubTitle(), AppConst.LANGUAGE_RN));

        response.setDescEn(getConfigValue(dataPackageEntity.getDescription(), AppConst.LANGUAGE_EN));
        response.setDescFr(getConfigValue(dataPackageEntity.getDescription(), AppConst.LANGUAGE_FR));
        response.setDescRn(getConfigValue(dataPackageEntity.getDescription(), AppConst.LANGUAGE_RN));

        if(dataPackageEntity.getStatus() == AppConst.STATUS_ON)
            response.setStatus(true);
        else response.setStatus(false);

        ImageDTO imageDTO = getImage(dataPackageEntity.getUrl());
        response.setImage(imageDTO);
        return response;
    }

    private void insertSub(DataPackageAddRequest request, String configKey,BigDecimal objId, String type) {
        insertAppConfig(request.getSubEn(),configKey,objId,type,AppConst.LANGUAGE_EN);
        insertAppConfig(request.getSubFr(),configKey,objId,type,AppConst.LANGUAGE_FR);
        insertAppConfig(request.getSubRn(),configKey,objId,type,AppConst.LANGUAGE_RN);
    }

    private void insertDesc(DataPackageAddRequest request, String objType, BigDecimal objId, String type) {
        insertAppConfig(request.getDescEn(),objType,objId,type,AppConst.LANGUAGE_EN);
        insertAppConfig(request.getDescFr(),objType,objId,type,AppConst.LANGUAGE_FR);
        insertAppConfig(request.getDescRn(),objType,objId,type,AppConst.LANGUAGE_RN);
    }
    private void insertNameAndTitle(DataPackageAddRequest request, String configKey, BigDecimal objId, String type) {
        insertAppConfig(request.getNameEn(),configKey,objId,type,AppConst.LANGUAGE_EN);
        insertAppConfig(request.getNameFr(),configKey,objId,type,AppConst.LANGUAGE_FR);
        insertAppConfig(request.getNameRn(),configKey,objId,type,AppConst.LANGUAGE_RN);
    }
    private void insertAppConfig(String desc, String objType, BigDecimal objId, String type,String lang){
        AppConfigEntity appConfigDescEn = appConfigRepository.getByConfigKeyAndLanguage(objType, lang);
        if(appConfigDescEn == null && Objects.nonNull(desc)  && StringUtil.isNotBlank(desc) && !StringUtil.isEmpty(desc)){
            appConfigDescEn = new AppConfigEntity();
            saveAppConfig(appConfigDescEn, desc, lang, objType, objId, type);
        }
        if(appConfigDescEn != null){
            saveAppConfig(appConfigDescEn, desc, lang, objType, objId, type);
        }
    }
    private String getConfigValue(String configKey, String language) {
        AppConfigEntity appConfig = appConfigRepository.findByObj(configKey, language);
        if (Objects.nonNull(appConfig))
            return StringUtils.convertUT8(appConfig.getConfigValue());
        else return null;
    }
}
