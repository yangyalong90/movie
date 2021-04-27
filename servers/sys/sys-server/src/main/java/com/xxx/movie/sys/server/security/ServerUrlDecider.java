package com.xxx.movie.sys.server.security;

import com.xxx.common.security.handler.url.UrlResourceDecider;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ServerUrlDecider implements UrlResourceDecider {
    @Override
    public void decision(Authentication authentication, HttpServletRequest request) throws AccessDeniedException {

        System.out.println(authentication);

    }
}
