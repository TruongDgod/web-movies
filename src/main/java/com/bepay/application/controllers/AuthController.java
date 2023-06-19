package com.bepay.application.controllers;

import com.bepay.application.config.JWTConfig;
import com.bepay.application.entities.db.AccountEntity;
import com.bepay.application.exceptions.AuthorizedException;
import com.bepay.application.exceptions.ParameterIllegalException;
import com.bepay.application.exceptions.SystemErrorException;
import com.bepay.application.repository.db.AccountRepository;
import com.bepay.application.security.JwtTokenUtil;
import com.bepay.application.constant.UrlConst;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.UserResponse;
import com.bepay.application.models.auth.LoginRequest;
import com.bepay.application.models.auth.LoginResponse;
import com.bepay.application.repository.db.AccountRepository;
import com.bepay.application.security.JwtTokenUtil;
import com.bepay.application.services.AuthService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author DATDEN
 * @see AuthController
 */

@RestController
@CrossOrigin
@RequestMapping(UrlConst.URL_AUTH)
public class AuthController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private AccountRepository accountRepository;


    @PostMapping(UrlConst.LOGIN)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        BaseResponse baseResponse = new BaseResponse();

        try {
            logger.info("#AuthController.login start", loginRequest.getUsername());

            /**
             * Pass Login
             */
//            UserResponse userResponse = authService.login(loginRequest);
//            if (userResponse == null) {
//                throw new AuthorizedException(getMessageSource("common.error.unauthorized"));
//            }
//            if (StringUtils.isBlank(userResponse.getUsername()) || StringUtils.isBlank(userResponse.getRoleCode())) {
//                throw new AuthorizedException(getMessageSource("common.error.unauthorized"));
//            }
//            String token = jwtTokenUtil.generateToken(userResponse.getUsername(), userResponse.getRoleCode());
//            Cookie cookie = new Cookie("access_token", token);
//            cookie.setMaxAge(jwtConfig.getExpired());
//            cookie.setHttpOnly(true);
//            cookie.setSecure(false);
//            cookie.setPath("/");
//            response.addCookie(cookie);
//            LoginResponse loginResponse = new LoginResponse();
//            loginResponse.setData(userResponse);
//            loginResponse.setAccessToken(token);
//            loginResponse.setMessage(getMessageSource("common.ok"));
//            logger.info("#AuthController.login success");
//            return ResponseEntity.ok(loginResponse);
            if (loginRequest.getUsername().equals("bepay_test123")
                    && loginRequest.getPassword().equals("bepay_test123")) {
                UserResponse userResponse = new UserResponse();
                userResponse.setUsername("bepay_test123");
                userResponse.setRoleCode("Admin");
//                userResponse.setRoleCode("Cv");
                String token = jwtTokenUtil.generateToken(userResponse.getUsername(), userResponse.getRoleCode());

                Cookie cookie = new Cookie("access_token", token);
                cookie.setMaxAge(jwtConfig.getExpired());
                cookie.setHttpOnly(true);
                cookie.setSecure(false);
                cookie.setPath("/");
                response.addCookie(cookie);
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setData(userResponse);
                loginResponse.setAccessToken(token);
                loginResponse.setMessage(getMessageSource("common.ok"));
                logger.info("#AuthController.login success");
                return ResponseEntity.ok(loginResponse);
            }
            return null;
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search ParameterIllegalException : " + ex.getMessage(), ex);
            baseResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            baseResponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search : " + ex.getMessage(), ex);
            baseResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            baseResponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search SystemErrorException : " + ex.getMessage(), ex);
            baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            baseResponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.LOGOUT)
    public ResponseEntity<?> logout(HttpServletResponse response) {
        BaseResponse bResponse = new BaseResponse();

        try {
            logger.info("#AuthController.logout start");
            Cookie cookie = new Cookie("access_token", null);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setSecure(true);
            response.addCookie(cookie);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setMessage(getMessageSource("common.ok"));
            logger.info("#AuthController.logout success");
            return ResponseEntity.ok(loginResponse);
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search ParameterIllegalException : " + ex.getMessage(), ex);
            bResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            bResponse.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(bResponse, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search : " + ex.getMessage(), ex);
            bResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            bResponse.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(bResponse, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search SystemErrorException : " + ex.getMessage(), ex);
            bResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            bResponse.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(bResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UrlConst.CHECK)
    public ResponseEntity<?> checkLogin(HttpServletRequest httpServletRequest) {
        BaseResponse response = new BaseResponse();

        try {

            logger.info("#AuthController.checkLogin start");
            Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(httpServletRequest.getCookies()).orElse(new Cookie[0]))
                    .filter(cookie -> "access_token".equals(cookie.getName()) && StringUtils.isNoneBlank(cookie.getValue()))
                    .findFirst();
            if (cookieAuth.isPresent() && StringUtils.isNotBlank(cookieAuth.get().getValue())) {
                logger.info("#AuthController.checkLogin success");
                String accessToken = cookieAuth.get().getValue();
                Claims cookie = jwtTokenUtil.decodeJWT(accessToken);
                if (cookie == null) throw new AuthorizedException(getMessageSource("common.error.unauthorized"));
                if (StringUtils.isBlank(cookie.get("username").toString()))
                    throw new AuthorizedException(getMessageSource("common.error.unauthorized"));
                AccountEntity accountEntity = accountRepository.findByUserName(cookie.get("username").toString());
                LoginRequest loginRequest = new LoginRequest(accountEntity.getUserName(), accountEntity.getPassWord());
                UserResponse userResponse = authService.login(loginRequest);
                if (userResponse == null) throw new AuthorizedException(getMessageSource("common.error.unauthorized"));
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setData(userResponse);
                loginResponse.setAccessToken(accessToken);
                loginResponse.setMessage(getMessageSource("common.ok"));
                logger.info("#AuthController.login success");
                return ResponseEntity.ok(loginResponse);
            } else {
                logger.info("#AuthController.checkLogin error");

                response.setHttpStatus(HttpStatus.UNAUTHORIZED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (ParameterIllegalException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search ParameterIllegalException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.param"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (AuthorizedException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(getMessageSource("common.error.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (SystemErrorException ex) {
            ex.printStackTrace();
            logger.error("#DataGroupController.search SystemErrorException : " + ex.getMessage(), ex);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(getMessageSource("common.system.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
