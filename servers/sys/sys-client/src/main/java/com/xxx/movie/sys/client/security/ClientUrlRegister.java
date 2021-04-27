package com.xxx.movie.sys.client.security;

import com.xxx.common.security.handler.url.SecurityUrlRegister;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(SecurityUrlRegister.class)
public class ClientUrlRegister extends SecurityUrlRegister {
    @Override
    public void register(RegisterBody registerBody) {

    }
}
