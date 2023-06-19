package com.bepay.application.controllers;

import com.bepay.application.constant.UrlConst;
import com.bepay.application.dto.CheckNameDto;
import com.bepay.application.dto.CodeDTO;
import com.bepay.application.dto.RoleDTO;
import com.bepay.application.dto.ServiceGroupDTO;
import com.bepay.application.dto.request.*;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.DataPackageResponseDetail;
import com.bepay.application.enums.RoleDataEnum;
import com.bepay.application.exceptions.AuthorizedException;
import com.bepay.application.exceptions.ParameterIllegalException;
import com.bepay.application.exceptions.SystemErrorException;
import com.bepay.application.services.DataPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(UrlConst.DATA_PACKAGE)
public class DataPackageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DataPackageController.class);
    @Autowired
    DataPackageService dataPackageService;

    @PostMapping(UrlConst.SEARCH)
    public ResponseEntity<?> search(@RequestBody DataPackageRequest request) {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.search Start...");
            reponse = dataPackageService.search(request);
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.search ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.search : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.search SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.GET_GROUP)
    public ResponseEntity<?> getGroup(@RequestBody DataPackageGetRoleRequest request) {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.getGroup Start...");
            List<ServiceGroupDTO> groups = dataPackageService.getGroup(request);
            reponse.setData(groups);
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getGroup ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getGroup : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getGroup SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.GET_ROLE)
    public ResponseEntity<?> getRole(@RequestBody SearchDetailRequest request) {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.getRole Start...");
            List<RoleDTO> roles = dataPackageService.getRole(request.getId());
            reponse.setData(roles);
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getRole ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getRole : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getRole SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(UrlConst.GET_ROLE)
    public ResponseEntity<?> getRoles() {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.getRoles Start...");
            reponse.setData(RoleDataEnum.getList().stream().map(item -> new RoleDTO(new BigDecimal(item.getId()), item.getDesc())).collect(Collectors.toList()));
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getRole ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getRole : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getRole SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.ADD)
    public ResponseEntity<?> add(@RequestBody DataPackageAddRequest request) {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.add Start...");
            reponse = dataPackageService.add(request);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.add ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.add : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.add SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DETAIL)
    public ResponseEntity<?> detail(@RequestBody SearchDetailRequest request) {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.detail Start...");
            DataPackageResponseDetail detail = dataPackageService.detail(request);
            reponse.setData(detail);
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.detail ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.detail : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.detail SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.UPDATE)
    public ResponseEntity<?> update(@RequestBody DataPackageAddRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            logger.info("#DataPackageController.update Start...");
            response = dataPackageService.update(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.update ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.update : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.update SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DELETE)
    public ResponseEntity<?> delete(@RequestBody SearchDetailRequest request) {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.delete Start...");
            dataPackageService.delete(request);
            reponse.setMessage(getMessageSource("common.delete.success"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.delete ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.delete : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.delete SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(UrlConst.STATUS)
    public ResponseEntity<?> getStatus() {
        BaseResponse response = new BaseResponse();
        try {
            response = dataPackageService.getStatus();
            response.setMessage(getMessageSource("common.ok"));
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.getStatus SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(UrlConst.LIST_ACTION)
    public ResponseEntity<?> searchGroup(@RequestBody DataPackageActionRequest request) {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.search Start...");
            reponse = dataPackageService.searchPackage(request);
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.searchGroup ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.searchGroup : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.searchGroup SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.DETAIL_ACTION)
    public ResponseEntity<?> detailAction(@RequestBody PromotionActionRequest request) {
        BaseResponse reponse = new BaseResponse();
        try {
            logger.info("#DataPackageController.detailAction Start...");
            reponse = dataPackageService.detailAction(request);
            reponse.setMessage(getMessageSource("common.ok"));
            reponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.detailAction ParameterIllegalException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.detailAction : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            reponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataPackageController.detailAction SystemErrorException : " + ex.getMessage(), ex);
            reponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            reponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody CodeDTO request) {
        logger.info("#DDataPackageController.add start... ");
        BaseResponse response = new BaseResponse();
        try {
            response = dataPackageService.check(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DDataPackageController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/check-name")
//    public ResponseEntity<?> checkName(@RequestBody CheckNameDto request) {
//        logger.info("#DataGroupController.checkName start... ");
//        BaseResponse response = new BaseResponse();
//        try{
//            response = dataPackageService.checkName(request);
//            return new ResponseEntity<>(response,HttpStatus.OK);
//        } catch (SystemErrorException ex) {
//            ex.printStackTrace();
//            logger.error("#DataGroupController.search SystemErrorException : " + ex.getMessage(), ex);
//            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            response.setMessage(getMessageSource("common.system.error"));
//            return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
