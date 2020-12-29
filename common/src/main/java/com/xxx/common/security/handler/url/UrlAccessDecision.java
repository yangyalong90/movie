package com.xxx.common.security.handler.url;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class UrlAccessDecision implements AccessDecisionManager {

    private UrlResourceDecider decider;

    public UrlAccessDecision(UrlResourceDecider decider) {
        this.decider = decider;
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        FilterInvocation invocation = (FilterInvocation) object;
        HttpServletRequest httpRequest = invocation.getHttpRequest();
        decider.decision(authentication, httpRequest);
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
