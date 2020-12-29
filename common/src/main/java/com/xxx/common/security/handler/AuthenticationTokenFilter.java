package com.xxx.common.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends BasicAuthenticationFilter {

    private static final String AUTH_HEADER = "X-Authorization";

    private TokenHandler tokenHandler;

    public AuthenticationTokenFilter(AuthenticationManager authenticationManager, TokenHandler tokenHandler) {
        super(authenticationManager);
        this.tokenHandler = tokenHandler;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        if (StringUtils.isEmpty(request.getHeader(AUTH_HEADER))) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(AUTH_HEADER);
        Authentication authentication = tokenHandler.detail(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
