package com.bepay.application.security;

import com.bepay.application.config.MessageUtil;
import com.bepay.application.dto.response.BaseResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;
	@Autowired
	private Gson gson;
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
		baseResponse.setMessage(MessageUtil.getMessage("common.error.unauthorized", "vi"));
		returnRes(baseResponse, response);
	}
	private void returnRes(BaseResponse baseResponse, HttpServletResponse response) throws IOException {
		String strRes = gson.toJson(baseResponse);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(strRes);
		out.flush();
	}
}
