package com.xxx.movie.sys.client.security;

import com.xxx.common.security.handler.url.SecurityUrlRegister;
import com.xxx.movie.sys.client.feign.AuthFeignClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
public class ClientUrlRegister extends SecurityUrlRegister {

    private AuthFeignClient authFeignClient;

    public ClientUrlRegister(AuthFeignClient authFeignClient) {
        this.authFeignClient = authFeignClient;
    }

    @Override
    public void register(RegisterBody registerBody) {
        authFeignClient.registerUrl(registerBody);
    }
}
