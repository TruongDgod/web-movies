package com.bepay.application.controllers;

import com.bepay.application.constant.UrlConst;
import com.bepay.application.dto.CodeDTO;
import com.bepay.application.dto.request.*;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.exceptions.AuthorizedException;
import com.bepay.application.exceptions.ParameterIllegalException;
import com.bepay.application.exceptions.SystemErrorException;
import com.bepay.application.services.DataGroupService;
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
@RequestMapping(UrlConst.DATA_GROUP)
public class DataGroupController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(DataGroupController.class);
    @Autowired
    private DataGroupService dataGroupService;
    @PostMapping(UrlConst.SEARCH)
    public ResponseEntity<?> search(@RequestBody DataGroupSearchRequest request) {
        logger.info("#DataGroupController.search start... ");
        BaseResponse reponse = new BaseResponse();
        try {
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            reponse = dataGroupService.search(request);
            return new ResponseEntity<>(reponse ,HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.ADD)
    public ResponseEntity<?> add(@RequestBody DataGroupRequestDTO request) {
        logger.info("#DataGroupController.add start... ");
        BaseResponse response = new BaseResponse();
        try{
            response = dataGroupService.add(request);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.add SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody CodeDTO request) {
        logger.info("#DataGroupController.check start... ");
        BaseResponse response = new BaseResponse();
        try{
            response = dataGroupService.check(request);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.check SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DETAIL)
    public ResponseEntity<?> detail(@RequestBody SearchDetailRequest request) {
        logger.info("#DataGroupController.detail start... ");
        BaseResponse reponse = new BaseResponse();
        try{
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            DataGroupRequestDTO dto = dataGroupService.getDetail(request);
            reponse.setData(dto);
            return new ResponseEntity<>(reponse,HttpStatus.OK);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.detail SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DELETE)
    public ResponseEntity<?> delete(@RequestBody SearchDetailRequest request) {
        logger.info("#DataGroupController.delete start... ");
        BaseResponse reponse = new BaseResponse();
        try{
            reponse.setMessage(getMessageSource("common.delete.success"));
            reponse.setHttpStatus(HttpStatus.OK);
            dataGroupService.delete(request);
            return new ResponseEntity<>(reponse,HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.delete ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.delete : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.delete SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.UPDATE)
    public ResponseEntity<?> update(@RequestBody DataGroupRequestDTO request){
        logger.info("#DataGroupController.update start... ");
        BaseResponse response = new BaseResponse();
        try{
            response = dataGroupService.update(request);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.update ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.update : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.update SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(UrlConst.STATUS)
    public ResponseEntity<?> getStatus(){
        BaseResponse response = new BaseResponse();
        try {
            response = dataGroupService.getStatus();
            response.setMessage(getMessageSource("common.ok"));
            response.setHttpStatus(HttpStatus.OK);
            return  new ResponseEntity<>(response ,HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
            logger.error("#DataGroupController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(UrlConst.GET_ROLE)
    public ResponseEntity<?> getRole(){
        BaseResponse response = new BaseResponse();
        try {
            response = dataGroupService.getRole();
            response.setMessage(getMessageSource("common.ok"));
            response.setHttpStatus(HttpStatus.OK);
            return  new ResponseEntity<>(response ,HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
            logger.error("#DataGroupController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DETAIL_ACTION)
    public ResponseEntity<?> detailAction(@RequestBody PromotionActionRequest request) {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.detailAction Start...");
            reponse = dataGroupService.searchPackage(request);
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.detailAction ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse ,HttpStatus.BAD_REQUEST);
        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.detailAction : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.detailAction SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(UrlConst.LIST_ACTION)
    public ResponseEntity<?> listAction() {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.listAction Start...");
            reponse = dataGroupService.listAction();
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.listAction ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse ,HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.listAction : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse ,HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.listAction SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
