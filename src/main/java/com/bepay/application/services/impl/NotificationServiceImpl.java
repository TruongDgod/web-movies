package com.bepay.application.services.impl;

import com.bepay.application.config.MessageUtil;
import com.bepay.application.constant.AppConst;
import com.bepay.application.dto.*;
import com.bepay.application.dto.request.NotificationRequestDTO;
import com.bepay.application.dto.request.SearchDetailRequest;
import com.bepay.application.dto.request.SearchRequest;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.NotificationResponseDTO;
import com.bepay.application.entities.db.*;
import com.bepay.application.enums.*;
import com.bepay.application.exceptions.ParameterIllegalException;
import com.bepay.application.exceptions.SystemErrorException;
import com.bepay.application.mapper.MessageMapper;
import com.bepay.application.repository.db.*;
import com.bepay.application.services.NoticationService;
import com.bepay.application.utils.AppUtil;
import com.bepay.application.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl extends AbstractServiceImpl implements NoticationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserListRepository userListRepository;
    @Autowired
    private UserSegmentRepository userSegmentRepository;
    @Autowired
    private MessageMapper messageMapper;


    @Autowired
    private ScheduleMessageRepository scheduleMessageRepository;
    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Override
    public BaseResponse search(SearchRequest request) {
        logger.info("#NoticationServiceImpl.search start... ");
        try {
            NotificationSearchStatusEnum statusEnum = NotificationSearchStatusEnum.getMap().get(request.getStatus());
            if (Objects.isNull(statusEnum)) return null;
            Pageable pageable = getPageable(request.getPage(), request.getSize(), false, "id");
            List<MessageDTO> listRes;
            Page<CMSMessageEntity> pageMessage;
            pageMessage = messageRepository.search(
                    request.getStartFromDate(),
                    request.getEndToDate(),
                    request.getTitle(),
                    checkNumber(request.getTitle())?new BigDecimal(request.getTitle()):null,
                    statusEnum.getKey(),
                    pageable);
            listRes = pageMessage.getContent().stream().map(e ->
                    messageMapper.convertToDTO(e)
            ).collect(Collectors.toList());
            return baseResponseSuccess(pageMessage, listRes);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#NoticationServiceImpl.search Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.error.system.eror"));
        }
    }

    @Override
    public BaseResponse add(NotificationRequestDTO request) throws ParseException, IOException {
        logger.info("#NoticationServiceImpl.add start... ");
        try {
            BaseResponse response = new BaseResponse();
            response.setMessage(getMessageSource("common.insert.success"));
            response.setHttpStatus(HttpStatus.OK);
            CMSMessageEntity messageEntity = new CMSMessageEntity();
            if(Objects.nonNull(request.getId())) messageEntity = messageRepository.findById(request.getId()).orElse( new CMSMessageEntity());

            messageRepository.saveAndFlush(messageEntity);
            messageEntity = insertMessage(request, messageEntity);

            TargetTypeEnum targetTypeEnum = TargetTypeEnum.getMap().get(request.getTargetType());
            if(Objects.isNull(request.getTargetType()))  throw new ParameterIllegalException(getMessageSource("common.error.param"));
            messageEntity.setTargetType(targetTypeEnum.getId());
            switch (targetTypeEnum) {
                case USER_LIST:
                    if (Objects.isNull(request.getFile()))
                        throw new SystemErrorException(getMessageSource("common.system.error"));
                    CMSFileUserListEntity fileUserListEntity  = insertUserList( request.getFile(), messageEntity.getId());
                    fileUploadRepository.save(fileUserListEntity);
                    break;
                case USER_SEGMENT:
                    for (UserSegmentDTO segmentDTO : request.getTarget().getSegment()) {
                        CMSUserSegmentEntity cmsUserSegmentEntity = insertUserSegment(segmentDTO, messageEntity.getId());
                        userSegmentRepository.saveAndFlush(cmsUserSegmentEntity);
                    }
                    break;
            }
            messageRepository.saveAndFlush(messageEntity);
            NotificationResponseDTO dto = getDataRespone(messageEntity);
            response.setData(dto);
            return response;
        } catch (Exception ex) {
            List<CMSUserListEntity> list = userListRepository.findByMessageId(request.getId());
            for (CMSUserListEntity e : list) {
                e.setIsDeleted(AppConst.IS_DELETE_DISENABLE);
                userListRepository.saveAndFlush(e);
            }
            ex.printStackTrace();
            logger.error("#NoticationServiceImpl.add Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    @Override
    public NotificationResponseDTO getDetail(SearchDetailRequest request) {
        logger.info("#NoticationServiceImpl.getDetail start... ");
        try {
            CMSMessageEntity messageEntity = messageRepository.findById(request.getId()).orElse(null);
            if (Objects.isNull(messageEntity)) {
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            }
            return getDataRespone(messageEntity);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#NoticationServiceImpl.getDetail Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    @Override
    public BaseResponse update(NotificationRequestDTO request) throws IOException, ParseException {
        logger.info("#NoticationServiceImpl.update start... ");
        BaseResponse response = new BaseResponse();
        response.setMessage(getMessageSource("common.update.success"));
        response.setHttpStatus(HttpStatus.OK);
        try {
            Optional<CMSMessageEntity> messageEntityOptional = messageRepository.findById(request.getId());
            if (!messageEntityOptional.isPresent())
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            CMSMessageEntity messageEntity = messageEntityOptional.get();
            messageEntity.setId(request.getId());
            messageEntity = insertMessage(request, messageEntity);
            TargetTypeEnum targetTypeEnum = TargetTypeEnum.getMap().get(request.getTargetType());
            if (Objects.isNull(targetTypeEnum))
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            messageEntity.setTargetType(targetTypeEnum.getId());
            switch (targetTypeEnum) {
                case USER_SEGMENT:
                    disableUserListAndFile(request.getId());
                    disableUserSegment(request.getId());
                    if (Objects.isNull(request.getTarget()))
                        throw new ParameterIllegalException(getMessageSource("common.error.param"));
                    for (UserSegmentDTO segmentDTO : request.getTarget().getSegment()) {
                        CMSUserSegmentEntity userSegmentEntity = insertUserSegment(segmentDTO, messageEntity.getId());
                        userSegmentRepository.saveAndFlush(userSegmentEntity);
                    }
                    break;
                case USER_LIST:
                    if (Objects.nonNull(request.getFile()) &&
                            (Objects.isNull(request.getFile().getIsFake()) ||
                                    !request.getFile().getIsFake()) &&
                            (Objects.nonNull(request.getFile().getFileName()))
                    ) {
                        disableFileUserList(request.getId());
                        disableUserSegment(request.getId());
                        if (Objects.isNull(request.getFile())) throw new ParameterIllegalException(getMessageSource("common.error.param"));
                        CMSFileUserListEntity fileUserListEntity = insertUserList(request.getFile(), messageEntity.getId());
                        fileUploadRepository.saveAndFlush(fileUserListEntity);
                    }
                    break;
            }
            messageRepository.saveAndFlush(messageEntity);
            response.setData(getDataRespone(messageEntity));

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#NoticationServiceImpl.update Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    private void disableUserList(BigDecimal messageId){
        List<CMSUserListEntity> cmsUserListEntityList = userListRepository.findByMessageId(messageId);
        cmsUserListEntityList.forEach(item -> item.setIsDeleted(AppConst.IS_DELETE_DISENABLE));
        userListRepository.saveAllAndFlush(cmsUserListEntityList);
    }

    private void disableUserListAndFile(BigDecimal messageId){
        disableFileUserList(messageId);
        disableUserList(messageId);
    }

    private void disableFileUserList(BigDecimal messageId){
        List<CMSFileUserListEntity> fileUserListEntityList = fileUploadRepository.findAllByMessageId(messageId);
        fileUserListEntityList.forEach(item -> item.setIsDeleted(AppConst.IS_DELETE_DISENABLE));
        fileUploadRepository.saveAllAndFlush(fileUserListEntityList);
    }

    private void disableUserSegment(BigDecimal messageId){
        List<CMSUserSegmentEntity> segmentEntityList = userSegmentRepository.findByMessageId(messageId);
        segmentEntityList.forEach(item -> item.setIsDeleted(AppConst.IS_DELETE_DISENABLE));
        userSegmentRepository.saveAllAndFlush(segmentEntityList);
    }


    @Override
    public void delete(SearchDetailRequest request) {
        logger.info("#NoticationServiceImpl.delete start... ");
        try {
            CMSMessageEntity messageEntity = messageRepository.findById(request.getId()).orElse(null);
            if (Objects.isNull(messageEntity)) {
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            }
            messageEntity.setIsDeleted(AppConst.IS_DELETE_DISENABLE);
            messageEntity.setStatus(AppConst.STATUS_COMPLETED);
            messageRepository.saveAndFlush(messageEntity);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#NoticationServiceImpl.update Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    @Override
    public BigDecimal saveUserList(MultipartFile request, BigDecimal messageId) {
        try {
            if (messageId == null) {
                messageId = messageRepository.save(new CMSMessageEntity()).getId();
            } else disableUserList(messageId);

            List<Double> listNumberPhone = new ArrayList<>();
            if (Objects.nonNull(request)) {
                XSSFWorkbook workbook = new XSSFWorkbook(request.getInputStream());
                XSSFSheet worksheet = workbook.getSheetAt(0);
                int col =   worksheet.getRow(3).getLastCellNum();
                if(col > 2)   throw new ParameterIllegalException(MessageUtil.getMessage("common.error.param"));
                for (int i = 3; i < worksheet.getPhysicalNumberOfRows(); i++) {
                    XSSFRow row = worksheet.getRow(i);

                    for(int j = 1; j < col; j ++){
                        double numberPhone = 0;
                        switch (row.getCell(j).getCellType()){
                            case NUMERIC:
                                numberPhone = row.getCell(j).getNumericCellValue();
                                listNumberPhone.add(numberPhone);
                                break;
                            case STRING:
                                numberPhone = Double.parseDouble(String.valueOf(row.getCell(j).getStringCellValue()));
                                listNumberPhone.add(numberPhone);
                                break;
                        }
                    }
                }
            }
            for (double i : listNumberPhone) {
                CMSUserListEntity userList =  insertOneUserList(new BigDecimal(i), messageId);
                userListRepository.saveAndFlush(userList);
            }
            return messageId;
        } catch (Exception ex) {
            throw new ParameterIllegalException(MessageUtil.getMessage("common.error.param"));
        }
    }

    @Override
    public BaseResponse copy(NotificationRequestDTO request) {
        logger.info("#NoticationServiceImpl.add start... ");
        try {
            NotificationResponseDTO dto = new NotificationResponseDTO();
            TargetTypeEnum targetTypeEnum = TargetTypeEnum.getMap().get(request.getTargetType());
            if (Objects.isNull(targetTypeEnum))
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            switch (targetTypeEnum) {
                case USER_LIST:
                    if (Objects.nonNull(request.getFile()) &&
                            (Objects.isNull(request.getFile().getIsFake()) ||
                                    request.getFile().getIsFake())
                    ) {
                        CMSMessageEntity messageEntity =  new CMSMessageEntity();
                        insertMessage(request, messageEntity);
                        messageEntity.setStatus(AppConst.STATUS_DRAFT);
                        messageRepository.saveAndFlush(messageEntity);

                        CMSFileUserListEntity fileUserListOld = fileUploadRepository.getByMessageId(request.getId()).orElse(null);
                        if(Objects.nonNull(fileUserListOld)){
                            CMSFileUserListEntity fileUserListNew = copyFileUserList(fileUserListOld, messageEntity.getId());
                            fileUploadRepository.saveAndFlush(fileUserListNew);
                        }

                        List<CMSUserListEntity> listUserList = userListRepository.findByMessageId(request.getId());
                        if (!listUserList.isEmpty()) {
                            for (CMSUserListEntity e : listUserList) {
                                CMSUserListEntity eNew = insertOneUserList(e.getPhoneNumber(), messageEntity.getId());
                                userListRepository.saveAndFlush(eNew);
                            }
                        }
                        dto = getDataRespone(messageEntity);
                        break;

                    } else {
                        CMSMessageEntity messageEntity =  messageRepository.findById(request.getId()).orElse( new CMSMessageEntity());
                        insertMessage(request, messageEntity);
                        messageEntity.setStatus(AppConst.STATUS_DRAFT);
                        messageRepository.saveAndFlush(messageEntity);

                        CMSFileUserListEntity fileUserListEntityNew = insertFileUserList(request,messageEntity.getId());
                        fileUploadRepository.saveAndFlush(fileUserListEntityNew);
                        dto = getDataRespone(messageEntity);
                    }
                    break;
                case USER_SEGMENT:
                    CMSMessageEntity messageEntity =  new CMSMessageEntity();
                    insertMessage(request, messageEntity);
                    messageEntity.setStatus(AppConst.STATUS_DRAFT);
                    messageRepository.saveAndFlush(messageEntity);


                    if(Objects.isNull(request.getTarget())) throw new ParameterIllegalException(MessageUtil.getMessage("common.error.param"));
                    for (UserSegmentDTO segmentDTO : request.getTarget().getSegment()) {
                        CMSUserSegmentEntity userSegmentEntity = convertToEntity(segmentDTO, messageEntity.getId());
                        userSegmentRepository.saveAndFlush(userSegmentEntity);
                    }
                    dto = getDataRespone(messageEntity);
                    break;
            }
            BaseResponse response = new BaseResponse();
            response.setData(dto);
            response.setMessage(getMessageSource("common.insert.success"));
            response.setHttpStatus(HttpStatus.OK);
            return response;
        } catch (Exception ex) {
            List<CMSUserListEntity> list = userListRepository.findByMessageId(request.getId());
            for (CMSUserListEntity e : list) {
                e.setIsDeleted(AppConst.IS_DELETE_DISENABLE);
                userListRepository.saveAndFlush(e);
            }
            ex.printStackTrace();
            logger.error("#NoticationServiceImpl.add Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    @Override
    public BaseResponse push(SearchDetailRequest request) {
        try {
            CMSMessageEntity entity = messageRepository.findById(request.getId()).orElse(null);
            if (Objects.isNull(entity)) {
                throw new ParameterIllegalException(getMessageSource("common.error.param"));
            }
            CMSMessageEntity entityNew = new CMSMessageEntity();
            entityNew = copyMessageEntity(entity,entityNew);
            messageRepository.saveAndFlush(entityNew);
            BigDecimal id =  entityNew.getId();
            CMSFileUserListEntity fileUserListEntity = fileUploadRepository.getByMessageId(request.getId()).orElse(null);
            if (Objects.nonNull(fileUserListEntity)) {
                CMSFileUserListEntity fileUserListNew = copyFileUserList(fileUserListEntity, id);
                fileUploadRepository.saveAndFlush(fileUserListNew);
            }
            List<CMSUserListEntity> listUserList = userListRepository.findByMessageId(request.getId());
            if (CollectionUtils.isNotEmpty(listUserList) && Objects.nonNull(listUserList.get(0))) {
                for (CMSUserListEntity e : listUserList) {
                    e = insertOneUserList(e.getPhoneNumber(), id);
                    userListRepository.saveAndFlush(e);
                }
            }
            List<CMSUserSegmentEntity> userSegmentEntityList = userSegmentRepository.findByMessageId(request.getId());
            if (CollectionUtils.isNotEmpty(userSegmentEntityList) && Objects.nonNull(userSegmentEntityList.get(0))) {
                for (CMSUserSegmentEntity e : userSegmentEntityList) {
                    e = copyUserSegment(e, id);
                    userSegmentRepository.saveAndFlush(e);
                }
            }
            BaseResponse response = new BaseResponse();
            NotificationResponseDTO dto = getDataRespone(entityNew);
            response.setData(dto);
            response.setMessage(getMessageSource("common.insert.success"));
            response.setHttpStatus(HttpStatus.OK);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#NoticationServiceImpl.push Exception : " + ex.getMessage(), ex);
            throw new SystemErrorException(getMessageSource("common.system.error"));
        }
    }

    @Override
    public BaseResponse checkFile(MultipartFile request ) {
        try {
            BaseResponse response = new BaseResponse();
            response.setMessage(getMessageSource("common.ok"));
            response.setHttpStatus(HttpStatus.OK);
            if (Objects.nonNull(request)) {
                XSSFWorkbook workbook = new XSSFWorkbook(request.getInputStream());
                XSSFSheet worksheet = workbook.getSheetAt(0);
                for (int i = 3; i < worksheet.getPhysicalNumberOfRows() ; i++) {
                    int col =   worksheet.getRow(i).getLastCellNum();
                    if(col > 2)   throw new ParameterIllegalException(MessageUtil.getMessage("common.error.format_file"));
                    XSSFRow row = worksheet.getRow(i);
                    if(org.apache.commons.lang3.StringUtils.isBlank(worksheet.getRow(3).getCell(1).toString())) throw new ParameterIllegalException(MessageUtil.getMessage("common.error.format_file_recognized"));
                    for(int j = 0; j < col; j ++){
                        double numberPhone = 0;
                        switch (row.getCell(j).getCellType()){
                            case NUMERIC:
                                numberPhone = row.getCell(j).getNumericCellValue();
                                if(j == 1 && !AppUtil.isLumitelNumber(String.valueOf(new BigDecimal(numberPhone))) && numberPhone != 0 ){
                                    throw new ParameterIllegalException(MessageUtil.getMessage("common.error.format_file_recognized"));
                                }
                                break;
                            case STRING:
                                numberPhone = Double.parseDouble(String.valueOf(row.getCell(j).getStringCellValue()));
                                if(j == 1 && !AppUtil.isLumitelNumber(String.valueOf(new BigDecimal(numberPhone))) && numberPhone != 0 ){
                                    throw new ParameterIllegalException(MessageUtil.getMessage("common.error.format_file_recognized"));
                                }
                                break;
                        }

                    }
                }
            }
            return response;
        } catch (Exception ex) {
            throw new ParameterIllegalException(MessageUtil.getMessage("common.error.format_file"));
        }
    }

    private NotificationResponseDTO getDataRespone(CMSMessageEntity messageEntity) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setTarget(new TargetDTO());
        dto.setId(messageEntity.getId());
        if (Objects.nonNull(messageEntity.getTitleEnglish())) dto.setTitleEn(StringUtils.convertUT8(messageEntity.getTitleEnglish()));
        if (Objects.nonNull(messageEntity.getTitleKirundi())) dto.setTitleRn(StringUtils.convertUT8(messageEntity.getTitleKirundi()));
        if (Objects.nonNull(messageEntity.getTitleFrench())) dto.setTitleFr(StringUtils.convertUT8(messageEntity.getTitleFrench()));
        if (messageEntity.getIsMultiLanguage() == AppConst.INT_MULTI_LANGUAGE) dto.setMultiLanguage(true);
        dto.setMultiLanguage(AppConst.STATUS_ON == messageEntity.getIsMultiLanguage());
        if (Objects.nonNull(messageEntity.getContentEnglish())) dto.setContentEn(StringUtils.convertUT8(messageEntity.getContentEnglish()));
        if (Objects.nonNull(messageEntity.getContentFrench())) dto.setContentFr(StringUtils.convertUT8(messageEntity.getContentFrench()));
        if (Objects.nonNull(messageEntity.getContentKirundi())) dto.setContentRn(StringUtils.convertUT8(messageEntity.getContentKirundi()));
        dto.setScheduleStatus(messageEntity.getScheduleStatus());
        dto.setScheduleTime(messageEntity.getTimeSend());
        dto.setNewsId(messageEntity.getNewsID());
        dto.setCategoryType(messageEntity.getType());
        dto.setTargetType(messageEntity.getTargetType());

        FileUploadDTO fileUpload = new FileUploadDTO();
        CMSFileUserListEntity entity = fileUploadRepository.getByMessageId(messageEntity.getId()).orElse(null);
        if (Objects.nonNull(entity)) {
            fileUpload.setFileName(entity.getFileName());
            fileUpload.setFileLength(entity.getFileLength());
            fileUpload.setFileModified(entity.getFileModified());
            fileUpload.setIsFake(true);
            dto.setFile(fileUpload);
        }

        List<CMSUserSegmentEntity> segmentEntitys = userSegmentRepository.findByMessageId(messageEntity.getId());
        if (Objects.nonNull(segmentEntitys)) {
            List<UserSegmentDTO> segmentDTOs = new ArrayList<>();
            for (CMSUserSegmentEntity segmentEntity : segmentEntitys) {
                UserSegmentDTO segmentDTO = new UserSegmentDTO();
                SegmentVersionDTO versionDTO = new SegmentVersionDTO();
                segmentDTO.setVersion(versionDTO);
                segmentDTO.setAppType(segmentEntity.getAppName());
                if (org.apache.commons.lang3.StringUtils.isNoneBlank(segmentEntity.getVersionCondition()) && segmentEntity.getVersionCondition().split(",").length == 2) {
                    segmentDTO.getVersion().setVersion(segmentEntity.getVersionCondition().split(",")[1]);
                    segmentDTO.getVersion().setOperator(segmentEntity.getVersionCondition().split(",")[0]);
                }
                segmentDTO.setPlatform(segmentEntity.getPlatform());
                segmentDTOs.add(segmentDTO);
            }
            dto.getTarget().setSegment(segmentDTOs);
        }
        return dto;
    }

    private CMSMessageEntity copyMessageEntity(CMSMessageEntity entity, CMSMessageEntity entityNew) {
        entityNew.setNewsID(entity.getNewsID());
        entityNew.setStatus(AppConst.STATUS_READY);
        entityNew.setScheduleStatus(AppConst.CMS_SCHEDULE_STATUS_PUBLISH_NOW);
        entityNew.setTimeSend(new Date());
        entityNew.setTitleEnglish(entity.getTitleEnglish());
        entityNew.setTitleFrench(entity.getTitleFrench());
        entityNew.setTitleKirundi(entity.getTitleKirundi());
        entityNew.setContentEnglish(entity.getContentEnglish());
        entityNew.setContentFrench(entity.getContentFrench());
        entityNew.setContentKirundi(entity.getContentKirundi());
        entityNew.setIsMultiLanguage(entity.getIsMultiLanguage());
        entityNew.setCreatedDate(new Date());
        entityNew.setUpdatedDate(new Date());
        entityNew.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        entityNew.setTestStatus(AppConst.CMS_TEST_STATUS_COMPLETED);
        entityNew.setType(entity.getType());
        entityNew.setCategory(entity.getCategory());
        entityNew.setTargetType(entity.getTargetType());
        return entityNew;
    }

    private CMSMessageEntity insertMessage(NotificationRequestDTO request, CMSMessageEntity messageEntity) {
        messageEntity.setCategory(AppConst.CATEGORY_PROMOTION);
        if (Objects.nonNull(request.getTitleEn())) {
            messageEntity.setTitleEnglish(StringUtils.convertWindows1252(request.getTitleEn()));
        }
        if (Objects.nonNull(request.getContentEn())) {
            messageEntity.setContentEnglish(StringUtils.convertWindows1252(request.getContentEn()));
        }
        if (Objects.nonNull(request.getTitleFr())) {
            messageEntity.setTitleFrench(StringUtils.convertWindows1252(request.getTitleFr()));
        }
        if (Objects.nonNull(request.getContentEn())) {
            messageEntity.setContentFrench(StringUtils.convertWindows1252(request.getContentFr()));
        }
        if (Objects.nonNull(request.getTitleRn())) {
            messageEntity.setTitleKirundi(StringUtils.convertWindows1252(request.getTitleRn()));
        }
        if (Objects.nonNull(request.getContentRn())) {
            messageEntity.setContentKirundi(StringUtils.convertWindows1252(request.getContentRn()));
        }
        if (Boolean.TRUE.equals(request.getMultiLanguage())) messageEntity.setIsMultiLanguage(AppConst.INT_MULTI_LANGUAGE);

        if (Objects.isNull(request.getSaveType())) throw new ParameterIllegalException(MessageUtil.getMessage("common.error.param"));
        ScheduleStatusEnum scheduleStatus = ScheduleStatusEnum.getMap().get(request.getSaveType());
        if (Objects.nonNull(scheduleStatus)) messageEntity.setScheduleStatus(scheduleStatus.getId());
        messageEntity.setScheduleStatus(AppConst.STATUS_READY);

        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getMap().get(request.getCategoryType());
        if (Objects.nonNull(categoryTypeEnum)) messageEntity.setType(categoryTypeEnum.getId());

        if(Objects.isNull(request.getMultiLanguage())) throw new ParameterIllegalException(MessageUtil.getMessage("common.error.param"));
        MultiLanguageEnum multiLanguageEnum = MultiLanguageEnum.get(request.getMultiLanguage());
        if (Objects.nonNull(multiLanguageEnum)) messageEntity.setIsMultiLanguage(multiLanguageEnum.getId());

        messageEntity.setCreatedDate(new Date());
        messageEntity.setUpdatedDate(new Date());
        messageEntity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        messageEntity.setNewsID(request.getNewsId());
        messageEntity.setTestStatus(AppConst.CMS_TEST_STATUS_COMPLETED);
        if(Objects.isNull(request.getTargetType())) throw new ParameterIllegalException(MessageUtil.getMessage("common.error.param"));
        TargetTypeEnum targetTypeEnum = TargetTypeEnum.getMap().get(request.getTargetType());
        if(Objects.nonNull(targetTypeEnum)) messageEntity.setTargetType(targetTypeEnum.getId());
        return insertMessageSchedule(messageEntity, request);
    }

    private CMSMessageEntity insertMessageSchedule(CMSMessageEntity messageEntity, NotificationRequestDTO request) {
        CMSScheduleMassageEntity scheduleMassageEntity = new CMSScheduleMassageEntity();
        ScheduleStatusEnum scheduleStatusEnum = ScheduleStatusEnum.getMap().get(request.getScheduleStatus());
        if(Objects.isNull(scheduleStatusEnum))  throw new ParameterIllegalException(MessageUtil.getMessage("common.error.param"));
        switch (scheduleStatusEnum) {
            case PUBLISH_NOW:
                messageEntity.setScheduleStatus(AppConst.CMS_SCHEDULE_STATUS_PUBLISH_NOW);
                scheduleMassageEntity.setRepeatTime(new Date());
                messageEntity.setTimeSend(new Date());
                break;
            case SCHEDULE:
                messageEntity.setStatus(AppConst.STATUS_READY);
                messageEntity.setScheduleStatus(AppConst.CMS_SCHEDULE_STATUS_SCHEDULE);
                messageEntity.setTimeSend(request.getScheduleTime());
                scheduleMassageEntity.setRepeatTime(request.getScheduleTime());
                break;
        }
        if(Objects.isNull(request.getSaveType())) throw new ParameterIllegalException(MessageUtil.getMessage("common.error.param"));
        SaveTypeEnum saveTypeEnum = SaveTypeEnum.getMap().get(request.getSaveType());
        if(Objects.isNull(saveTypeEnum))  throw new ParameterIllegalException(MessageUtil.getMessage("common.error.param"));
        switch (saveTypeEnum) {
            case DRAFT:
                messageEntity.setStatus(AppConst.STATUS_DRAFT);
                break;
            case PUBLISH_NOW:
                messageEntity.setStatus(AppConst.STATUS_READY);
                break;
        }
        scheduleMassageEntity.setMessageId(messageEntity.getId());
        scheduleMassageEntity.setCreatedDate(new Date());
        scheduleMassageEntity.setUpdatedDate(new Date());
        scheduleMassageEntity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        scheduleMessageRepository.saveAndFlush(scheduleMassageEntity);
        return messageEntity;
    }

    private CMSFileUserListEntity insertUserList( FileUploadDTO file, BigDecimal messageId) {
        CMSFileUserListEntity fileUserListEntity = new CMSFileUserListEntity();
        fileUserListEntity.setFileName(file.getFileName());
        fileUserListEntity.setFileLength(file.getFileLength());
        fileUserListEntity.setFileModified(file.getFileModified());
        fileUserListEntity.setMessageId(messageId);
        fileUserListEntity.setCreatedDate(new Date());
        fileUserListEntity.setUpdatedDate(new Date());
        fileUserListEntity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        return fileUserListEntity;
    }

    private CMSUserSegmentEntity insertUserSegment(UserSegmentDTO segmentDTO, BigDecimal messageId) {
        CMSUserSegmentEntity userSegmentEntity = new CMSUserSegmentEntity();
        userSegmentEntity.setMessageID(messageId);
        userSegmentEntity.setAppName(segmentDTO.getAppType());
        String versionCondition = "";
        if(Objects.nonNull(segmentDTO.getVersion())){
            if(Objects.nonNull(segmentDTO.getVersion().getOperator())) versionCondition += segmentDTO.getVersion().getOperator();
            if(Objects.nonNull(segmentDTO.getVersion().getVersion())) versionCondition += "," + segmentDTO.getVersion().getVersion();
        }
        userSegmentEntity.setVersionCondition(versionCondition);
        userSegmentEntity.setPlatform(segmentDTO.getPlatform());
        userSegmentEntity.setCreatedDate(new Date());
        userSegmentEntity.setUpdatedDate(new Date());
        userSegmentEntity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        return userSegmentEntity;
    }

    private CMSUserSegmentEntity convertToEntity(UserSegmentDTO segmentDTO, BigDecimal id) {
        CMSUserSegmentEntity userSegmentEntity = new CMSUserSegmentEntity();
        userSegmentEntity.setMessageID(id);
        userSegmentEntity.setAppName(segmentDTO.getAppType());
        if (Objects.nonNull(segmentDTO.getVersion()) &&
                org.apache.commons.lang3.StringUtils.isNoneBlank(segmentDTO.getVersion().getOperator()) &&
                org.apache.commons.lang3.StringUtils.isNoneBlank(segmentDTO.getVersion().getVersion())
        ) {
            userSegmentEntity.setVersionCondition(segmentDTO.getVersion().getOperator() + "," + segmentDTO.getVersion().getVersion());
        }
        userSegmentEntity.setPlatform(segmentDTO.getPlatform());
        userSegmentEntity.setCreatedDate(new Date());
        userSegmentEntity.setUpdatedDate(new Date());
        userSegmentEntity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        return userSegmentEntity;
    }

    private CMSFileUserListEntity copyFileUserList(CMSFileUserListEntity fileUserListEntityList, BigDecimal messageId) {
        CMSFileUserListEntity fileUserListNew = new CMSFileUserListEntity();
        fileUserListNew.setFileLength(fileUserListEntityList.getFileLength());
        fileUserListNew.setFileName(fileUserListEntityList.getFileName());
        fileUserListNew.setFileModified(fileUserListEntityList.getFileModified());
        fileUserListNew.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        fileUserListNew.setMessageId(messageId);
        fileUserListNew.setCreatedDate(new Date());
        fileUserListNew.setUpdatedDate(new Date());
        return fileUserListNew;
    }

    private CMSUserListEntity insertOneUserList(BigDecimal phoneNumber, BigDecimal id) {
        CMSUserListEntity entity = new CMSUserListEntity();
        entity.setMessageID(id);
        entity.setPhoneNumber(phoneNumber);
        entity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        entity.setCreatedDate(new Date());
        entity.setUpdatedDate(new Date());
        return entity;
    }

    private CMSFileUserListEntity insertFileUserList(NotificationRequestDTO request, BigDecimal messageId) {
        CMSFileUserListEntity fileUserListEntityNew = new CMSFileUserListEntity();
        fileUserListEntityNew.setFileName(request.getFile().getFileName());
        fileUserListEntityNew.setFileLength(request.getFile().getFileLength());
        fileUserListEntityNew.setFileModified(request.getFile().getFileModified());
        fileUserListEntityNew.setMessageId(messageId);
        fileUserListEntityNew.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        fileUserListEntityNew.setCreatedDate(new Date());
        fileUserListEntityNew.setUpdatedDate(new Date());
        return fileUserListEntityNew;
    }
    private CMSFileUserListEntity copyFileUserList(CMSFileUserListEntity fileOld) {
        CMSFileUserListEntity fileUserListEntityNew = new CMSFileUserListEntity();
        fileUserListEntityNew.setFileName(fileOld.getFileName());
        fileUserListEntityNew.setFileLength(fileOld.getFileLength());
        fileUserListEntityNew.setFileModified(fileOld.getFileModified());
        fileUserListEntityNew.setMessageId(fileOld.getMessageId());
        fileUserListEntityNew.setCreatedDate(new Date());
        fileUserListEntityNew.setUpdatedDate(new Date());
        return fileUserListEntityNew;
    }

    private boolean checkNumber(String content){
        try {
            BigDecimal number = new BigDecimal(content);
            return true;
        } catch (Exception ex){
            return false;
        }
    }
    private CMSUserSegmentEntity copyUserSegment(CMSUserSegmentEntity e, BigDecimal id) {
        CMSUserSegmentEntity userSegmentEntity = new CMSUserSegmentEntity();
        userSegmentEntity.setAppName(e.getAppName());
        userSegmentEntity.setVersionCondition(e.getVersionCondition());
        userSegmentEntity.setPlatform(e.getPlatform());
        userSegmentEntity.setMessageID(id);
        userSegmentEntity.setIsDeleted(AppConst.IS_DELETE_ENABLE);
        userSegmentEntity.setCreatedDate(new Date());
        userSegmentEntity.setUpdatedDate(new Date());
        return userSegmentEntity;
    }


}

