package com.xxx.common.security.handler.url;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface UrlResourceDecider {

    void decision(Authentication authentication, HttpServletRequest request) throws AccessDeniedException;

}
