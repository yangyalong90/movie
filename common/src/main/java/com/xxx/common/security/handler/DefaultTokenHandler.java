package com.xxx.common.security.handler;

import com.xxx.common.security.detail.UserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultTokenHandler implements TokenHandler {

    private Map<String, Authentication> map = new HashMap<>();

    @Override
    public Authentication token(UserDetail detail) {
        String uuid = UUID.randomUUID().toString();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(detail, uuid);
        map.put(uuid, token);
        return token;
    }

    @Override
    public Authentication detail(String token) {
        return map.get(token);
    }
}
