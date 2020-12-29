package com.xxx.movie.zuul.security.decision;

import com.xxx.common.security.handler.url.UrlResourceDecider;
import com.xxx.movie.user.common.model.auth.UrlResource;
import com.xxx.movie.user.export.feign.UserFeignClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Component
public class ZuulResourceDecider implements UrlResourceDecider {

    private UserFeignClient userFeignClient;

    private RouteLocator routeLocator;

    public ZuulResourceDecider(UserFeignClient userFeignClient, RouteLocator routeLocator) {
        this.userFeignClient = userFeignClient;
        this.routeLocator = routeLocator;
    }

    @Override
    public void decision(Authentication authentication, HttpServletRequest request) throws AccessDeniedException {
        String requestURI = request.getRequestURI();

        Route route = routeLocator.getMatchingRoute(requestURI);

        if (route == null) {
            return;
        }

        String application = route.getId();
        String path = route.getPath();

        List<UrlResource> urlResources = userFeignClient.ignoreUrls(application);
        boolean ignoreMatch = urlResources.stream()
                .anyMatch(
                        urlResource -> {
                            if (!urlResource.getApplication().equals(application)) {
                                return false;
                            }
                            return urlResource.getUrls()
                                    .stream()
                                    .anyMatch(s -> {
                                        PathMatcher pathMatcher = new AntPathMatcher();
                                        return pathMatcher.match(s, path);
                                    });
                        });

        if (authentication instanceof AnonymousAuthenticationToken && !ignoreMatch) {
            throw new AccessDeniedException("");
        }

        System.out.println(requestURI);
    }


}
