package com.xxx.common.rest.handlers;

import com.xxx.common.rest.annotations.ApiVersion;
import com.xxx.common.rest.condition.ApiVersionRequestCondition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    private Map<String, ApiVersion> apiMaxVersion = new HashMap<>();

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {



        super.registerHandlerMethod(handler, method, mapping);
    }

    @Override
    protected ApiVersionRequestCondition getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);

        if (apiVersion != null) {
            return new ApiVersionRequestCondition(apiVersion.value());
        }

        return null;
    }
}
