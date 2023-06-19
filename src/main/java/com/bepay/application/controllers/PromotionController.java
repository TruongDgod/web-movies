package com.bepay.application.controllers;

import com.bepay.application.constant.UrlConst;
import com.bepay.application.dto.request.PromotionRequestDTO;
import com.bepay.application.dto.request.SearchDetailRequest;
import com.bepay.application.dto.request.SearchRequest;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.PromotionResponseDTO;
import com.bepay.application.exceptions.AuthorizedException;
import com.bepay.application.exceptions.ParameterIllegalException;
import com.bepay.application.exceptions.SystemErrorException;
import com.bepay.application.services.PromotionSevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(UrlConst.PROMOTION)
public class PromotionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PromotionController.class);
    @Autowired
    private PromotionSevice promotionSevice;

    @PostMapping(UrlConst.ADD)
    public ResponseEntity<?> add(@RequestBody PromotionRequestDTO dto) {
        logger.info("#PromotionController.add start... ");
        BaseResponse response = new BaseResponse();
        try {
            response = promotionSevice.add(dto);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.SEARCH)
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        logger.info("#PromotionController.search start... ");
        BaseResponse response = new BaseResponse();
        try {
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage(getMessageSource("common.ok"));
            response = promotionSevice.search(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.UPDATE)
    public ResponseEntity<?> update(@RequestBody PromotionRequestDTO dto) {
        logger.info("#PromotionController.update start... ");
        BaseResponse response = new BaseResponse();
        try {
            response = promotionSevice.update(dto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DETAIL)
    public ResponseEntity<?> detail(@RequestBody SearchDetailRequest request) {
        logger.info("#PromotionController.detail start... ");
        BaseResponse response = new BaseResponse();
        try {
            PromotionResponseDTO dto = promotionSevice.getDetail(request);
            response.setMessage(getMessageSource("common.ok"));
            response.setHttpStatus(HttpStatus.OK);
            response.setData(dto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DELETE)
    public ResponseEntity<?> delete(@RequestBody SearchDetailRequest request) {
        logger.info("#PromotionController.delete start... ");
        BaseResponse response = new BaseResponse();
        try {
            promotionSevice.delete(request);
            response.setMessage(getMessageSource("common.delete.success"));
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(UrlConst.LIST_FEATURE)
    public ResponseEntity<?> listFeature() {
        logger.info("#PromotionController.detail start... ");
        BaseResponse response = new BaseResponse();
        try {
            response = promotionSevice.getFeature();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#PromotionController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
