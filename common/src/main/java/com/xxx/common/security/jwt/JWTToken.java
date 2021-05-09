package com.xxx.common.security.jwt;

import com.xxx.common.security.detail.UserDetail;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTToken extends AbstractAuthenticationToken {

    private String token;
    private UserDetail detail;

    public JWTToken(String token, UserDetail detail) {
        super(detail == null ? null : detail.getAuthorities());
        this.token = token;
        this.detail = detail;
    }

    @Override
    public String getCredentials() {
        return token == null ? "" : token;
    }

    @Override
    public UserDetail getPrincipal() {
        return detail;
    }
}
