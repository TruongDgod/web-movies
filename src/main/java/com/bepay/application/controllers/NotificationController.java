package com.bepay.application.controllers;

import com.bepay.application.constant.UrlConst;
import com.bepay.application.dto.request.NotificationRequestDTO;
import com.bepay.application.dto.request.SearchDetailRequest;
import com.bepay.application.dto.request.SearchRequest;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.NotificationResponseDTO;
import com.bepay.application.entities.db.CMSFileUserListEntity;
import com.bepay.application.entities.db.CMSUserListEntity;
import com.bepay.application.exceptions.AuthorizedException;
import com.bepay.application.exceptions.ParameterIllegalException;
import com.bepay.application.exceptions.SystemErrorException;
import com.bepay.application.repository.db.FileUploadRepository;
import com.bepay.application.repository.db.UserListRepository;
import com.bepay.application.services.NoticationService;
import com.bepay.application.utils.FileUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.*;


@Controller
@RequestMapping(UrlConst.NOTICATION)
public class NotificationController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @Autowired
    private NoticationService noticationService;

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @PostMapping(UrlConst.SEARCH)
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        logger.info("#NoticationController.search start... ");
        BaseResponse response = new BaseResponse();
        try {
            response = noticationService.search(request);
            response.setMessage(getMessageSource("common.ok"));
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = UrlConst.ADD)
    public ResponseEntity<?> add(@RequestBody NotificationRequestDTO request) {
        logger.info("#NoticationController.add start... ");
        BaseResponse response = new BaseResponse();
        try {
            response = noticationService.add(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (ParseException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = UrlConst.DOWNLOAD)
    public ResponseEntity<InputStreamResource> download(HttpServletRequest request) throws IOException {
        logger.info("#NoticationController.download start... ");
        HttpHeaders responseHeader = new HttpHeaders();
        BaseResponse response = new BaseResponse();
        try {
            ClassPathResource file = new ClassPathResource("file/Template_upload_userlist.xlsx");
            byte[] data = FileCopyUtils.copyToByteArray(file.getInputStream());
            // Set mimeType trả về
            responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // Thiết lập thông tin trả về
            responseHeader.set("Content-disposition", "attachment; filename=" + "Template_upload_userlist.xlsx");
            responseHeader.setContentLength(data.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.download SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<InputStreamResource>((MultiValueMap<String, String>) response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.UPDATE)
    public ResponseEntity<?> update(@RequestBody NotificationRequestDTO request) {
        logger.info("#NoticationController.update start... ");
        BaseResponse response = new BaseResponse();
        try {
            response = noticationService.update(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException | IOException | ParseException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DETAIL)
    public ResponseEntity<?> detail(@RequestBody SearchDetailRequest request) {
        logger.info("#NoticationController.detail start... ");
        BaseResponse response = new BaseResponse();
        try {
            NotificationResponseDTO dto = noticationService.getDetail(request);
            response.setData(dto);
            response.setMessage(getMessageSource("common.ok"));
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DELETE)
    public ResponseEntity<?> delete(@RequestBody SearchDetailRequest request) {
        logger.info("#NoticationController.delete start... ");
        BaseResponse response = new BaseResponse();
        try {
            noticationService.delete(request);
            response.setMessage(getMessageSource("common.delete.success"));
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/read-file")
    public ResponseEntity<?> readFile(@RequestBody MultipartFile request, @RequestParam(required = false) BigDecimal messageId) {
        logger.info("#NoticationController.delete start... ");
        BaseResponse response = new BaseResponse();
        try {
            if (Objects.isNull(request)) {
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                response.setMessage(getMessageSource("common.error.param"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            BigDecimal messageIdTmp = noticationService.saveUserList(request,messageId);
            response.setData(messageIdTmp);
            response.setMessage(getMessageSource("common.ok"));
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/copy")
    public ResponseEntity<?> copy(@RequestBody NotificationRequestDTO request) {
        logger.info("#NoticationController.add start... ");
        BaseResponse response = new BaseResponse();
        try {
            response = noticationService.copy(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/push")
    public ResponseEntity<?> push(@RequestBody SearchDetailRequest request) {
        logger.info("#NoticationController.push start... ");
        BaseResponse response = new BaseResponse();
        try {
            response = noticationService.push(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/check-file")
    public ResponseEntity<?> checkFile(@RequestBody MultipartFile request) {
        logger.info("#NoticationController.checkFile start... ");
        BaseResponse response = new BaseResponse();
        try {
            if (Objects.isNull(request)) {
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                response.setMessage(getMessageSource("common.error.param"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response = noticationService.checkFile(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.checkFile ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.format_file_recognized"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.checkFile SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/download-pdf")
    public ResponseEntity<InputStreamResource> downloadPdf(@RequestBody SearchDetailRequest request ) throws IOException {
        logger.info("#NoticationController.download start... ");
        HttpHeaders responseHeader = new HttpHeaders();
        BaseResponse response = new BaseResponse();
        try {
            if(Objects.isNull(request.getId())){
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                response.setMessage(getMessageSource("common.error.param"));
                return new ResponseEntity<InputStreamResource>((MultiValueMap<String, String>) response, HttpStatus.BAD_REQUEST);
            }
            Map<String,String> listRecord = getRecordData(request);
            InputStream inp = FileUtil.exportMapToExcel(listRecord);
            String fileName = getFileName(request);
            byte[] data = IOUtils.toByteArray(inp);
            inp.close();

            // Set mimeType trả về
            responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // Thiết lập thông tin trả về
            responseHeader.set("Content-disposition", "attachment; filename=" + fileName);
            responseHeader.setContentLength(data.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#NotificationController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<InputStreamResource>((MultiValueMap<String, String>) response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getFileName(SearchDetailRequest request) {
        String fileName = "userlist-upload.xlsx";
        CMSFileUserListEntity fileUserList = fileUploadRepository.getByMessageId(request.getId()).orElse(null);
        if(Objects.nonNull(fileUserList)){
            fileName = fileUserList.getFileName();
        }
        return fileName;
    }

    private Map<String, String> getRecordData(SearchDetailRequest request) {
        Map<String,String> listRecord = new  HashMap<>();
        listRecord.put("Please enter account phone number list into \"Account Number\" column and number of wallet accounts into \"No\" column like the below examples.", "");
        listRecord.put("","");
        listRecord.put("No","Account No");
        List<CMSUserListEntity> userListEntities = userListRepository.findByMessageId(request.getId());
        for (int i = 0; i < userListEntities.size(); i ++){
            listRecord.put(String.valueOf(i+1),userListEntities.get(i).getPhoneNumber().toString());
        }
        return listRecord;
    }

}
