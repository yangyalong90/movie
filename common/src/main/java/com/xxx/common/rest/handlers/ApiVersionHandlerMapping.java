package com.xxx.common.rest.handlers;

import com.xxx.common.rest.annotations.ApiVersion;
import com.xxx.common.rest.condition.ApiVersionRequestCondition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    private Map<RequestMappingInfoHashEqual, ApiVersionRequestCondition> apiMaxVersion = new HashMap<>();

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {

        if (mapping.getCustomCondition() instanceof ApiVersionRequestCondition) {
            handleMaxVersion(mapping);
            handleFullPathMapping(mapping, method, handler);
        }

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

    void handleMaxVersion(RequestMappingInfo mapping) {
        ApiVersionRequestCondition cc = (ApiVersionRequestCondition) mapping.getCustomCondition();
        RequestMappingInfoHashEqual infoHashEqual = new RequestMappingInfoHashEqual(mapping);
        ApiVersionRequestCondition versionRequestCondition = apiMaxVersion.get(infoHashEqual);
        if (versionRequestCondition == null) {
            apiMaxVersion.put(infoHashEqual, cc);
            cc.setMaxVersion(true);
        } else {
            if (cc.getVersion().compareTo(versionRequestCondition.getVersion()) >= 0) {
                cc.setMaxVersion(true);
                versionRequestCondition.setMaxVersion(false);
                apiMaxVersion.put(infoHashEqual, cc);
            }
        }
    }

    void handleFullPathMapping(RequestMappingInfo mapping, Method method, Object handler) {
        ApiVersionRequestCondition cc = (ApiVersionRequestCondition) mapping.getCustomCondition();

        // 处理 oldFullPath
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        if (apiVersion == null) {
            return;
        }
        String[] oldFullPath = apiVersion.oldFullPath();
        if (oldFullPath.length == 0) {
            return;
        }

        RequestMappingInfo mappingInfo = RequestMappingInfo.paths(oldFullPath).build();

        RequestMappingInfo requestMappingInfo = new RequestMappingInfo(mappingInfo.getName(),
                RequestMappingInfo.paths(oldFullPath).build().getPatternsCondition(),
                mapping.getMethodsCondition(), mapping.getParamsCondition(),
                mapping.getHeadersCondition(), mapping.getConsumesCondition(),
                mapping.getProducesCondition(), null);

        super.registerHandlerMethod(handler, method, requestMappingInfo);
    }

    @Override
    protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
        return super.getHandlerInternal(request);
    }

    static class RequestMappingInfoHashEqual {
        private final PatternsRequestCondition patternsCondition;

        private final RequestMethodsRequestCondition methodsCondition;

        private final ParamsRequestCondition paramsCondition;

        private final HeadersRequestCondition headersCondition;

        private final ConsumesRequestCondition consumesCondition;

        private final ProducesRequestCondition producesCondition;

        public RequestMappingInfoHashEqual(RequestMappingInfo mappingInfo) {
            patternsCondition = mappingInfo.getPatternsCondition();
            methodsCondition = mappingInfo.getMethodsCondition();
            paramsCondition = mappingInfo.getParamsCondition();
            headersCondition = mappingInfo.getHeadersCondition();
            consumesCondition = mappingInfo.getConsumesCondition();
            producesCondition = mappingInfo.getProducesCondition();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RequestMappingInfoHashEqual that = (RequestMappingInfoHashEqual) o;
            return Objects.equals(patternsCondition, that.patternsCondition) &&
                    Objects.equals(methodsCondition, that.methodsCondition) &&
                    Objects.equals(paramsCondition, that.paramsCondition) &&
                    Objects.equals(headersCondition, that.headersCondition) &&
                    Objects.equals(consumesCondition, that.consumesCondition) &&
                    Objects.equals(producesCondition, that.producesCondition);
        }

        @Override
        public int hashCode() {
            return Objects.hash(patternsCondition, methodsCondition, paramsCondition, headersCondition, consumesCondition, producesCondition);
        }
    }

}
