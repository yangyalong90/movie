package com.xxx.common.security.handler;

import com.alibaba.fastjson.JSON;
import com.xxx.common.model.ApiResult;
import com.xxx.common.security.cache.TokenCache;
import com.xxx.common.security.detail.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private TokenCache tokenCache;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        writeResponse(ApiResult.error(exception.getMessage()), response);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        Authentication token = tokenHandler.token(userDetail);
        tokenCache.put(token.getCredentials().toString(), userDetail);
        writeResponse(ApiResult.success(token.getCredentials().toString()), response);
    }


    void writeResponse(ApiResult apiResult, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(200);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(apiResult));
    }

}
