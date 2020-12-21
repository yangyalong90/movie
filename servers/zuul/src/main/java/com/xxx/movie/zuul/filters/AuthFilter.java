package com.xxx.movie.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xxx.movie.user.export.feign.UserFeignClient;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private static final String AUTH_HEADER = "AUTH_HEADER";

    private UserFeignClient userFeignClient;

    public AuthFilter(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String authHeader = request.getHeader(AUTH_HEADER);
        String URL = request.getRequestURL().toString();

        boolean permission = userFeignClient.permission(authHeader, URL);

        if (permission) {

            throw new ZuulRuntimeException(new ZuulException("a", 401, "b"));

//            HttpServletResponse response = currentContext.getResponse();
//            currentContext.setSendZuulResponse(false);
//
//            response.setContentType(ContentType.APPLICATION_JSON.toString());
//
//            currentContext.setResponseBody("{" +
//                    "code: 0" +
//                    "}");
//            currentContext.setResponseStatusCode(401);
        }

        return null;
    }

    void noAuth(){

    }

}
