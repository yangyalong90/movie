package com.xxx.movie.sys.client.security;

import com.xxx.common.security.handler.url.UrlResourceDecider;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public class ClientUrlDecider implements UrlResourceDecider {
    @Override
    public void decision(Authentication authentication, HttpServletRequest request) throws AccessDeniedException {

    }
}
