package com.bepay.application.services.impl;

import com.bepay.application.constant.AppConst;
import com.bepay.application.dto.DataActionDTO;
import com.bepay.application.dto.ImageDTO;
import com.bepay.application.dto.request.PromotionRequestDTO;
import com.bepay.application.dto.request.SearchDetailRequest;
import com.bepay.application.dto.request.SearchRequest;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.NewsResponse;
import com.bepay.application.dto.response.PromotionResponseDTO;
import com.bepay.application.entities.db.ActionFeatureEntity;
import com.bepay.application.entities.db.AppConfigEntity;
import com.bepay.application.entities.db.AppImageEntity;
import com.bepay.application.entities.db.NewsEntity;
import com.bepay.application.enums.AppTypeEnum;
import com.bepay.application.enums.NewsTypeEnum;
import com.bepay.application.enums.PromotionSeacrhStatusEnum;
import com.bepay.application.exceptions.ParameterIllegalException;
import com.bepay.application.exceptions.SystemErrorException;
import com.bepay.application.mapper.ActionFeatureMapper;
import com.bepay.application.mapper.NewsMapper;
import com.bepay.application.repository.db.ActionRepository;
import com.bepay.application.repository.db.AppConfigRepository;
import com.bepay.application.repository.db.AppImageRepository;
import com.bepay.application.repository.db.NewsRepository;
import com.bepay.application.services.PromotionSevice;
import com.bepay.application.utils.StringUtils;
import org.apache.commons.io.FileUtils;
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


@Service
public class PromotionSeviceImpl extends AbstractServiceImpl implements PromotionSevice {
    private static final Logger logger = LoggerFactory.getLogger(PromotionSeviceImpl.class);
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private AppConfigRepository appConfigRepository;
    @Autowired
    private NewsMapper newsMappper;
    @Autowired
    private AppImageRepository appImageRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private ActionFeatureMapper actionFeatureMapper;

    @Override
    public BaseResponse search(SearchRequest request) {
        logger.info("#PromotionSeviceImpl.search start... ");
        try {
            PromotionSeacrhStatusEnum promotionSeacrhStatusEnum = PromotionSeacrhStatusEnum.getMap().get(request.getStatus());
            if (Objects.isNull(promotionSeacrhStatusEnum)) return null;
            Page<NewsEntity> newsPage = null;
            List<NewsResponse> listRes = null;
            Pageable pageable = getPageable(request.getPage(), request.getSize(), false, "id");
            newsPage = newsRepository.search(pageable,
                    request.getStartFromDate(),
                    request.getEndToDate(),
                    promotionSeacrhStatusEnum.getValue(),
                    request.getTitle());
            listRes = newsPage.getContent().stream().map(this::convertToResponse).collect(Collectors.toList());
            return baseResponseSuccess(newsPage, listRes);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#PromotionSeviceImpl.search Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    @Override
    public BaseResponse add(PromotionRequestDTO dto) {
        logger.info("#PromotionSeviceImpl.add start... ");
        BaseResponse response = new BaseResponse();
        try {
            NewsEntity news = new NewsEntity();
            newsRepository.saveAndFlush(news);
            news = insertNews(news, dto);
            newsRepository.saveAndFlush(news);
            /// insert table AppConfig
            // EN
            insertConfig(news, dto, AppConst.LANGUAGE_EN, dto.getTitleEn(), dto.getShortContentEn(), dto.getFullContentEn());
            if (Boolean.TRUE.equals(dto.getMultiLanguage())) {
                insertConfig(news, dto, AppConst.LANGUAGE_FR, dto.getTitleFr(), dto.getShortContentFr(), dto.getFullContentFr());
                insertConfig(news, dto, AppConst.LANGUAGE_RN, dto.getTitleRn(), dto.getShortContentRn(), dto.getFullContentRn());
            }

            if (!Objects.isNull(dto.getImageBanner())) {
                insertImage(dto.getMultiLanguage(), AppConst.BANNER, news.getId(), dto.getImageBanner());
            }
            if (!Objects.isNull(dto.getImageHeader())) {
                insertImage(dto.getMultiLanguage(), AppConst.HEADER, news.getId(), dto.getImageHeader());
            }
            if (!Objects.isNull(dto.getImageAvatar())) {
                insertImage(dto.getMultiLanguage(), AppConst.AVATAR, news.getId(), dto.getImageAvatar());
            }
            PromotionResponseDTO data = getDataResponse(news);
            response.setData(data);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage(getMessageSource("common.insert.success"));
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#PromotionSeviceImpl.add Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }


    @Override
    public BaseResponse update(PromotionRequestDTO dto) {
        logger.info("#PromotionSeviceImpl.update start... ");
        BaseResponse response = new BaseResponse();
        try {
            NewsEntity news = newsRepository.getById(dto.getId());
            if (Objects.isNull(news)) {
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            }
            news = insertNews(news, dto);
            news.setUpdateTime(new Date());
            newsRepository.saveAndFlush(news);

            insertConfig(news, dto, AppConst.LANGUAGE_EN, dto.getTitleEn(), dto.getShortContentEn(), dto.getFullContentEn());
            if (Boolean.TRUE.equals(dto.getMultiLanguage())) {
                insertConfig(news, dto, AppConst.LANGUAGE_FR, dto.getTitleFr(), dto.getShortContentFr(), dto.getFullContentFr());
                insertConfig(news, dto, AppConst.LANGUAGE_RN, dto.getTitleRn(), dto.getShortContentRn(), dto.getFullContentRn());

                if (Objects.isNull(dto.getImageBanner()) || Objects.isNull(dto.getImageHeader()) || Objects.isNull(dto.getImageAvatar())) {
                    response.setHttpStatus(HttpStatus.BAD_REQUEST);
                    response.setMessage(getMessageSource("common.error.param"));
                    return response;
                }

            } else {
               disableMultiLanguage(AppConst.LANGUAGE_FR, news);
               disableMultiLanguage(AppConst.LANGUAGE_RN, news);
            }
            insertImage(dto.getMultiLanguage(), AppConst.BANNER, news.getId(), dto.getImageBanner());
            insertImage(dto.getMultiLanguage(), AppConst.HEADER, news.getId(), dto.getImageHeader());
            insertImage(dto.getMultiLanguage(), AppConst.AVATAR, news.getId(), dto.getImageAvatar());
            PromotionResponseDTO data = getDataResponse(news);
            response.setData(data);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage(getMessageSource("common.update.success"));
            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#PromotionSeviceImpl.update Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    @Override
    public PromotionResponseDTO getDetail(SearchDetailRequest request) {
        logger.info("#PromotionSeviceImpl.getDetail start... ");
        try {
            NewsEntity news = newsRepository.getById(request.getId());
            if (Objects.isNull(news)) {
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            }
            return getDataResponse(news);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#PromotionSeviceImpl.getDetail Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    private PromotionResponseDTO getDataResponse(NewsEntity news) throws IOException {
        PromotionResponseDTO response = new PromotionResponseDTO();
        response.setId(news.getId());
        NewsTypeEnum newsTypeEnum = NewsTypeEnum.get(news.getNewsType());
        if (Objects.nonNull(newsTypeEnum)) response.setNewsType(NewsTypeEnum.get(news.getNewsType()).getCode());
        AppTypeEnum appTypeEnum = AppTypeEnum.getMapCode().get(news.getClientType());
        if (Objects.nonNull(appTypeEnum)) response.setRoleType(appTypeEnum.getId());
        if (Objects.nonNull(news.getStartTime())) response.setStartTime(news.getStartTime());
        if (Objects.nonNull(news.getEndTime())) response.setEndTime(news.getEndTime());

        response.setMultiLanguage(news.getMultiLanguage() == AppConst.INT_MULTI_LANGUAGE);
        if (Objects.nonNull(news.getStatus())) response.setStatus(news.getStatus() == AppConst.STATUS_ON);
        if (Objects.nonNull(news.getShowAtHome())) response.setShowAtHome(news.getShowAtHome() == AppConst.STATUS_ON);
        if (Objects.nonNull(news.getShowOnTop())) response.setShowOnTop(news.getShowOnTop() == AppConst.STATUS_ON);
        if (Objects.nonNull(news.getHotNews())) response.setHotNews(news.getHotNews() == AppConst.STATUS_ON);

        response.setTitleEn(StringUtils.convertUT8(getConfigValue(news.getTitle(), AppConst.LANGUAGE_EN)));
        response.setShortContentEn(StringUtils.convertUT8(getConfigValue(news.getShortContent(), AppConst.LANGUAGE_EN)));
        response.setFullContentEn(StringUtils.convertUT8(getConfigValue(news.getFullContent(), AppConst.LANGUAGE_EN)));

        response.setTitleFr(StringUtils.convertUT8(getConfigValue(news.getTitle(), AppConst.LANGUAGE_FR)));
        response.setShortContentFr(StringUtils.convertUT8(getConfigValue(news.getShortContent(), AppConst.LANGUAGE_FR)));
        response.setFullContentFr(StringUtils.convertUT8(getConfigValue(news.getFullContent(), AppConst.LANGUAGE_FR)));

        response.setTitleRn(StringUtils.convertUT8(getConfigValue(news.getTitle(), AppConst.LANGUAGE_RN)));
        response.setShortContentRn(StringUtils.convertUT8(getConfigValue(news.getShortContent(), AppConst.LANGUAGE_RN)));
        response.setFullContentRn(StringUtils.convertUT8(getConfigValue(news.getFullContent(), AppConst.LANGUAGE_RN)));

        response.setImageBanner(getImage(news.getId(), AppConst.LANGUAGE_EN, AppConst.BANNER));
        response.setImageHeader(getImage(news.getId(), AppConst.LANGUAGE_EN, AppConst.HEADER));
        response.setImageAvatar(getImage(news.getId(), AppConst.LANGUAGE_EN, AppConst.AVATAR));


        if (Objects.nonNull(news.getTransactionData()) && org.apache.commons.lang3.StringUtils.isNoneBlank(news.getTransactionData())) {
            response.setTransactionData(news.getTransactionData());
            response.setActionType(1);
        } else response.setActionType(0);
        return response;
    }

    private String getConfigValue(String configKey, String language) {
        AppConfigEntity appConfig = appConfigRepository.findByObj(configKey, language);
        if (Objects.nonNull(appConfig))
            return appConfig.getConfigValue();
        else return null;
    }

    private ImageDTO getImage(BigDecimal id, String lang, String type) throws IOException {
        try {
            AppImageEntity appImageEntity = appImageRepository.getImageByObjId(id, type, lang);
            ImageDTO image = new ImageDTO();
            Path currentRelativePath = Paths.get("").toAbsolutePath().normalize();
            File file = new File(currentRelativePath + "/../assets" + appImageEntity.getUrl());
            byte[] bytes = FileUtils.readFileToByteArray(file);
            image.setFile("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bytes));
            String imageName = appImageEntity.getUrl().replace(AppConst.URL_IMAGE, "");
            int index = imageName.lastIndexOf("/") + 1;
            image.setName(imageName.substring(index, imageName.length()));
            image.setStatus(appImageEntity.getStatus() == AppConst.STATUS_ON);
            return image;
        } catch (Exception ex) {
            return new ImageDTO(null, null, false);
        }

    }

    @Override
    public void delete(SearchDetailRequest request) {
        logger.info("#PromotionSeviceImpl.delete start... ");
        try {
            NewsEntity news = newsRepository.getById(request.getId());
            news.setStatus(AppConst.STATUS_OFF);
            news.setIsDeleted(AppConst.IS_DELETE_DISENABLE);
            newsRepository.saveAndFlush(news);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#PromotionSeviceImpl.delete Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    @Override
    public BaseResponse getFeature() {
        BaseResponse response = new BaseResponse();
        try {
            response.setMessage(getMessageSource("common.ok"));
            List<ActionFeatureEntity> list = actionRepository.findAll();
            List<DataActionDTO> listRes = list.stream().map(e -> actionFeatureMapper.convertToDTO(e)).collect(Collectors.toList());
            response.setHttpStatus(HttpStatus.OK);
            response.setData(listRes);
            return response;
        } catch (Exception ex){
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    private NewsResponse convertToResponse(NewsEntity e)  {
       try {
           String title = null;
           String content = null;
           List<AppConfigEntity> appConfigEntitysTitle = appConfigRepository.findByObj(e.getTitle());
           for (AppConfigEntity entity : appConfigEntitysTitle) {
               if (entity.getLanguage().equals(AppConst.LANGUAGE_EN)) title = StringUtils.convertUT8(entity.getConfigValue());
           }
           List<AppConfigEntity> appConfigEntitysContent = appConfigRepository.findByObj(e.getShortContent());
           for (AppConfigEntity entity : appConfigEntitysContent) {
               if (entity.getLanguage().equals(AppConst.LANGUAGE_EN)) content = StringUtils.convertUT8(entity.getConfigValue());
           }
           return newsMappper.convertEntityToResponse(e, title, content);
       } catch (Exception ex){
           logger.error("convertToResponse err");
           return null;
       }
    }

    private AppConfigEntity saveAppConfig(String language, String objValue, BigDecimal objId, String configKey, String configValue, String objType) {
        AppConfigEntity appConfigEntity = new AppConfigEntity();
        appConfigEntity.setLanguage(language);
        appConfigEntity.setStatus(AppConst.STATUS_ON);
        appConfigEntity.setObjType(AppConst.NEWS);
        appConfigEntity.setObjId(objId);
        appConfigEntity.setObjValue(objValue);
        appConfigEntity.setConfigKey(configKey);
        appConfigEntity.setConfigValue(StringUtils.convertWindows1252(configValue));
        appConfigEntity.setCreateTime(new Date());
        appConfigEntity.setUpdateTime(new Date());
        return appConfigEntity;
    }

    private AppConfigEntity updateAppConfig(AppConfigEntity appConfigEntity, String configValue) {
        appConfigEntity.setConfigValue(StringUtils.convertWindows1252(configValue));
        appConfigEntity.setUpdateTime(new Date());
        return appConfigEntity;
    }

    private void saveImage(AppImageEntity appImageEntity, String lang, String type, BigDecimal objId, String objType, ImageDTO image) throws IOException {
        Path currentRelativePath = Paths.get("");
        appImageRepository.saveAndFlush(appImageEntity);
//        String fileName = StringUtil.subNameFile(image.getName());
        String fileName = StringUtils.subNameFile(UUID.randomUUID().toString() + objId + ".png");
        String uploadDir = currentRelativePath.toAbsolutePath() + "/../assets" + AppConst.URL_IMAGE + appImageEntity.getId();
        Path uploadPath = Paths.get(uploadDir, fileName).toAbsolutePath().normalize();
        String encodedImg = image.getFile().split(",")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));
        FileUtils.writeByteArrayToFile(new File(uploadPath.toString()), decodedBytes);
        appImageEntity.setImageType(type);
        appImageEntity.setLanguage(lang);
        appImageEntity.setObjType(AppConst.NEWS);
        appImageEntity.setObjId(objId);
        appImageEntity.setCreateTime(new Date());
        appImageEntity.setUrl(AppConst.URL_IMAGE + appImageEntity.getId() + "/" + fileName);
        if (Boolean.TRUE.equals(image.getStatus())) appImageEntity.setStatus(AppConst.STATUS_ON);
        else appImageEntity.setStatus(AppConst.STATUS_OFF);
        appImageEntity.setUpdateTIme(new Date());
        appImageRepository.saveAndFlush(appImageEntity);
    }

    private void insertImage(Boolean isMultiLanguage, String type, BigDecimal id, ImageDTO image) throws IOException {
        insertImageEntity(type, id, image, AppConst.LANGUAGE_EN);
        if (Boolean.TRUE.equals(isMultiLanguage)) {
            insertImageEntity(type, id, image, AppConst.LANGUAGE_FR);
            insertImageEntity(type, id, image, AppConst.LANGUAGE_RN);
        }
    }

    private void insertImageEntity(String type, BigDecimal id, ImageDTO image, String lang) throws IOException {
        logger.info("#PromotionSeviceImpl.insertImageEntity start... ");
        AppImageEntity appImageEn = appImageRepository.getImageByObjId(id, type, lang);
        if (Objects.isNull(appImageEn)) appImageEn = new AppImageEntity();
        saveImage(appImageEn, lang, type, id, AppConst.PROMOTION, image);
    }

    private void insertConfig(NewsEntity newsEntity, PromotionRequestDTO dto, String language, String title, String shortContent, String fullContent) {
        List<AppConfigEntity> entityList =  new ArrayList<>();
        AppConfigEntity titleConfig =  insertAppConfig(title, newsEntity.getTitle(), newsEntity.getId(), NewsTypeEnum.get(dto.getNewsType()).getMessage(), language, AppConst.TITLE);
        entityList.add(titleConfig);
        AppConfigEntity shortContentConfig = insertAppConfig(shortContent, newsEntity.getShortContent(), newsEntity.getId(), NewsTypeEnum.get(dto.getNewsType()).getMessage(), language, AppConst.SHORT_CONTENT);
        entityList.add(shortContentConfig);
        AppConfigEntity fullContentConfig = insertAppConfig(fullContent, newsEntity.getFullContent(), newsEntity.getId(), NewsTypeEnum.get(dto.getNewsType()).getMessage(), language, AppConst.FULL_CONTENT);
        entityList.add(fullContentConfig);
        appConfigRepository.saveAllAndFlush(entityList);

    }

    private AppConfigEntity insertAppConfig(String value, String configkey, BigDecimal newsId, String type, String language, String objValue) {
        if (!Objects.isNull(value) && !org.apache.commons.lang3.StringUtils.isBlank(value)) {
            AppConfigEntity appConfigEntity = appConfigRepository.findByObj(configkey, language);
            if (Objects.isNull(appConfigEntity)) {
                return saveAppConfig(language, objValue, newsId, configkey, value, type);
            } else {
                return updateAppConfig(appConfigEntity, value);
            }
        }
        return null;
    }

    private void disableMultiLanguage(String language, NewsEntity news) {
       disableAppConfig(news.getTitle(), language);
       disableAppConfig(news.getShortContent(), language);
       disableAppConfig(news.getFullContent(), language);
    }

    private void disableAppConfig(String type, String language) {
        AppConfigEntity appConfig = appConfigRepository.findByObj(type, language);
        if (Objects.nonNull(appConfig)) {
            appConfig.setStatus(AppConst.STATUS_OFF);
            appConfigRepository.saveAndFlush(appConfig);
        }
    }

    private NewsEntity insertNews(NewsEntity newsEntity, PromotionRequestDTO dto) {

        AppTypeEnum appTypeEnum = AppTypeEnum.getMap().get(dto.getRoleType());
        if (Objects.nonNull(appTypeEnum)) newsEntity.setClientType(appTypeEnum.getCode());

        newsEntity.setStartTime(dto.getStartTime());
        newsEntity.setEndTime(dto.getEndTime());
        newsEntity.setTitle(AppConst.NEWS_TITLE + newsEntity.getId());
        newsEntity.setContentType(AppConst.PROMOTION_CONTENT_TYPE_HTML);
        newsEntity.setTitle(AppConst.NEWS_TITLE + newsEntity.getId());
        newsEntity.setShortContent(AppConst.NEWS_SHORT_CONTENT + newsEntity.getId());
        newsEntity.setFullContent(AppConst.NEWS_FULL_CONTENT + newsEntity.getId());
        newsEntity.setMultiLanguage(AppConst.INT_NOT_MULTI_LANGUAGE);

        NewsTypeEnum newsTypeEnum = NewsTypeEnum.get(dto.getNewsType());
        if (Objects.isNull(newsTypeEnum)) throw new ParameterIllegalException(getMessageSource("common.error.param"));
        newsEntity.setNewsType(newsTypeEnum.getMessage());
        switch (newsTypeEnum) {
            case PROMOTION:
                newsEntity.setShowAtHome(AppConst.STATUS_OFF);
                if (Boolean.TRUE.equals(dto.getShowAtHome())) newsEntity.setShowAtHome(AppConst.STATUS_ON);

                newsEntity.setHotNews(AppConst.STATUS_OFF);
                if (Boolean.TRUE.equals(dto.getHotNews())) newsEntity.setHotNews(AppConst.STATUS_ON);

                newsEntity.setShowOnTop(AppConst.STATUS_OFF);
                if (Boolean.TRUE.equals(dto.getShowOnTop())) newsEntity.setShowOnTop(AppConst.STATUS_ON);

                newsEntity.setStatus(AppConst.STATUS_OFF);
                if (Boolean.TRUE.equals(dto.getStatus())) newsEntity.setStatus(AppConst.STATUS_ON);
                break;

            default:
                newsEntity.setShowAtHome(null);
                newsEntity.setHotNews(null);
                newsEntity.setShowOnTop(null);
                newsEntity.setStatus(AppConst.STATUS_ON);
                break;
        }
        if (Boolean.TRUE.equals(dto.getMultiLanguage())) newsEntity.setMultiLanguage(AppConst.INT_MULTI_LANGUAGE);
        newsEntity.setTransactionData(dto.getTransactionData());
        newsEntity.setCreateTime(new Date());
        newsEntity.setUpdateTime(new Date());
        newsEntity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        return newsEntity;
    }

}
