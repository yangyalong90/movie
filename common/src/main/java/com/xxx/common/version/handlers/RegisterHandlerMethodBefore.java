package com.xxx.common.version.handlers;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.reflect.Method;

public interface RegisterHandlerMethodBefore {

    void execute(Object handler, Method method, RequestMappingInfo mapping);

}
