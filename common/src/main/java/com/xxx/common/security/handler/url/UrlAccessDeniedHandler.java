package com.xxx.common.security.handler.url;

import com.alibaba.fastjson.JSON;
import com.xxx.common.model.ApiResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class UrlAccessDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        writeResponse(ApiResult.error("无权访问"), response);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        writeResponse(ApiResult.error("请先登录"), response);
    }

    void writeResponse(ApiResult apiResult, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(200);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(apiResult));
    }
}
