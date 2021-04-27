package com.xxx.movie.sys.client.security;

import com.xxx.common.security.handler.url.UrlResourceDecider;
import com.xxx.movie.sys.client.feign.AuthFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ClientUrlDecider implements UrlResourceDecider {

    private AuthFeignClient authFeignClient;

    @Value("${spring.application.name}")
    private String application;

    public ClientUrlDecider(AuthFeignClient authFeignClient) {
        this.authFeignClient = authFeignClient;
    }

    @Override
    public void decision(Authentication authentication, HttpServletRequest request) throws AccessDeniedException {
        boolean permission = authFeignClient.permission(authentication.getCredentials().toString(), application, request.getRequestURI());
        if (!permission) {
            throw new AccessDeniedException("");
        }
    }
}
