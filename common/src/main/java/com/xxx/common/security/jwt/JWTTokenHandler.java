package com.xxx.common.security.jwt;

import com.xxx.common.security.detail.UserDetail;
import com.xxx.common.security.handler.TokenHandler;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTTokenHandler implements TokenHandler {

    @Override
    public JWTToken token(UserDetail detail) {
        String uuid = UUID.randomUUID().toString();
        JWTToken token = new JWTToken(uuid, detail);
        return token;
    }

    @Override
    public JWTToken detail(String token) {
        return new JWTToken(token, null);
    }
}
