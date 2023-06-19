package com.bepay.application.controllers;

import com.bepay.application.config.LocaleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author DanTran
 * @return
 * @see BaseController
 */

@Service
public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private MessageSource messageSource;

    @Autowired
    HttpServletRequest httpServletRequest;


    @Autowired
    private HttpSession httpSession;


    public String getMessageSource(String key) {
        return messageSource.getMessage(key, null, LocaleConfig.getInstance().getLocale());
    }

}
