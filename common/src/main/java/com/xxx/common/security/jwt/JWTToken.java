package com.xxx.common.security.jwt;

import com.xxx.common.security.detail.UserDetail;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTToken extends AbstractAuthenticationToken {

    private String token;
    private UserDetail detail;

    public JWTToken(String token, UserDetail detail) {
        super(detail.getAuthorities());
        this.token = token;
        this.detail = detail;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return detail;
    }
}
