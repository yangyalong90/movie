package com.xxx.common.rest.handlers;

import com.xxx.common.rest.annotations.ApiVersion;
import com.xxx.common.rest.condition.ApiVersionRequestCondition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    private Map<RequestMappingInfoHashEqual, ApiVersionRequestCondition> apiMaxVersion = new HashMap<>();

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {

        RequestCondition<?> customCondition = mapping.getCustomCondition();
        if (customCondition instanceof ApiVersionRequestCondition) {
            ApiVersionRequestCondition cc = (ApiVersionRequestCondition) customCondition;
            RequestMappingInfoHashEqual infoHashEqual = new RequestMappingInfoHashEqual(mapping);
            ApiVersionRequestCondition versionRequestCondition = apiMaxVersion.get(infoHashEqual);
            if (versionRequestCondition == null) {
                apiMaxVersion.put(infoHashEqual, cc);
                cc.setMaxVersion(true);
            }else {
                if (cc.getVersion().compareTo(versionRequestCondition.getVersion()) >= 0) {
                    cc.setMaxVersion(true);
                    versionRequestCondition.setMaxVersion(false);
                    apiMaxVersion.put(infoHashEqual, cc);
                }
            }

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
