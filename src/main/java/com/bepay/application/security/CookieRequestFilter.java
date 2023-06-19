package com.bepay.application.security;

import com.bepay.application.config.JWTConfig;
import com.bepay.application.config.LocaleConfig;
import com.bepay.application.config.MessageUtil;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.utils.DateUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CookieRequestFilter extends OncePerRequestFilter {

    public static final String COOKIE_NAME = "access_token";
    public static final String AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private Gson gson;

    @Autowired
    private JWTConfig jwtConfig;

    public List<String> AUTH_WHITELIST = Arrays.asList(
            "/auth/login", "/auth/logout", "/auth/check");
//    public List<String> AUTH_WHITELIST = Arrays.asList(
//            "/");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String servletPath = request.getServletPath();
            String requestTokenHeader = request.getHeader(AUTHORIZATION);
            String accessToken = null;
            boolean match = AUTH_WHITELIST.stream().anyMatch(servletPath::startsWith);
            setLangResponse(request);
            if (match) {
                filterChain.doFilter(request, response);
                return;
            }
            Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                    .filter(cookie -> COOKIE_NAME.equals(cookie.getName()) && StringUtils.isNoneBlank(cookie.getValue()))
                    .findFirst();
            if (cookieAuth.isPresent() && StringUtils.isNoneBlank(cookieAuth.get().getValue())) {
                accessToken = cookieAuth.get().getValue();
            }
            if (StringUtils.isBlank(accessToken) &&
                    StringUtils.isNoneBlank(requestTokenHeader) &&
                    requestTokenHeader.startsWith(TOKEN_PREFIX)
            ) {
                accessToken = requestTokenHeader.replace(TOKEN_PREFIX, "");
            }
            if (StringUtils.isBlank(accessToken)) {
                responseUnauthorized(response);
                return;
            }

            Claims token = jwtTokenUtil.decodeJWT(accessToken);
            if (token == null) {//tuy nghiệp vụ check role check username
                responseUnauthorized(response);
                return;
            }

//            int TIME_15_MINUTE = 900;
            int TIME_15_MINUTE = 3700;
            if (token.getExpiration().getTime() < DateUtil.addSeconds(new Date(), TIME_15_MINUTE).getTime()) {
                String username = token.get("username", String.class);
                String roleCode = token.get("roleCode", String.class);
                if (StringUtils.isBlank(username) || StringUtils.isBlank(roleCode)) {
                    responseUnauthorized(response);
                    return;
                }
                String newToken = jwtTokenUtil.generateToken(username, roleCode);
                Cookie cookieNull = new Cookie(COOKIE_NAME, null);
                cookieNull.setMaxAge(0);
                cookieNull.setHttpOnly(true);
//                cookieNull.setSecure(true);
                cookieNull.setPath("/");
                response.addCookie(cookieNull);
                Cookie cookie = new Cookie(COOKIE_NAME, newToken);
                cookie.setMaxAge(jwtConfig.getExpired());
                cookie.setHttpOnly(true);
//                cookie.setSecure(true);
                cookie.setPath("/");
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(request.getHeader(AUTHORIZATION))) {
                    response.addHeader(AUTHORIZATION, TOKEN_PREFIX + newToken);
                }
            }

            filterChain.doFilter(request, response);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void responseUnauthorized(HttpServletResponse response) throws IOException {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        baseResponse.setMessage(MessageUtil.getMessage("common.error.unauthorized", "vi"));
        returnRes(baseResponse, response);
    }

    private void setLangResponse(HttpServletRequest request) {


        Locale locale;
        String lang = request.getHeader("lang");
        if (StringUtils.isBlank(lang)) lang = "en";
        switch (lang) {
            case "vi":
                locale = new Locale("vi", "VN");
                break;
            case "km":
                locale = new Locale("km", "KH");
                break;
            default:
                locale = new Locale("en", "US");

        }
        LocaleConfig.getInstance().setLocale(locale);

    }

    private void returnRes(BaseResponse baseResponse, HttpServletResponse response) throws IOException {
        String strRes = gson.toJson(baseResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        PrintWriter out = response.getWriter();
        out.print(strRes);
        out.flush();
    }
}
