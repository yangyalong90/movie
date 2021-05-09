package com.xxx.common.rest.config;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xxx.common.version.handlers.ApiVersionHandlerMapping;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class RestConfig extends WebMvcConfigurationSupport {

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ApiVersionHandlerMapping();
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PageLocalWebInterceptor());
        super.addInterceptors(registry);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    static class PageLocalWebInterceptor extends PageHelper implements HandlerInterceptor {
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            // PageHelper.clearPage() 内部调用 LOCAL_PAGE.remove()
            PageHelper.clearPage();
        }
    }
}
