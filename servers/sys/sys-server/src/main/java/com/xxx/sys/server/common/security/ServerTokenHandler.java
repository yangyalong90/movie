package com.xxx.sys.server.common.security;

import com.xxx.common.security.cache.TokenCache;
import com.xxx.common.security.jwt.JWTToken;
import com.xxx.common.security.jwt.JWTTokenHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ServerTokenHandler extends JWTTokenHandler {

    private TokenCache tokenCache;

    public ServerTokenHandler(@Lazy TokenCache tokenCache) {
        this.tokenCache = tokenCache;
    }

    @Override
    public JWTToken detail(String token) {
        return new JWTToken(token, tokenCache.get(token));
    }
}
