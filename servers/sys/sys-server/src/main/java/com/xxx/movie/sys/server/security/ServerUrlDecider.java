package com.xxx.movie.sys.server.security;

import com.xxx.common.security.handler.url.UrlResourceDecider;
import com.xxx.movie.sys.server.dao.UrlMapper;
import com.xxx.movie.sys.server.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ServerUrlDecider implements UrlResourceDecider {

    private AuthService authService;

    @Value("${spring.application.name}")
    private String application;

    public ServerUrlDecider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void decision(Authentication authentication, HttpServletRequest request) throws AccessDeniedException {

        boolean permission = authService.permission(authentication.getCredentials().toString(), application, request.getRequestURI());
        if (!permission) {
            throw new AccessDeniedException("");
        }

    }
}
