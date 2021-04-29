package com.xxx.movie.sys.client.register;

import com.xxx.common.register.AppRegister;
import com.xxx.movie.sys.client.feign.AuthFeignClient;
import org.springframework.stereotype.Component;

@Component
public class ClientRegister extends AppRegister {

    private AuthFeignClient authFeignClient;

    public ClientRegister(AuthFeignClient authFeignClient) {
        this.authFeignClient = authFeignClient;
    }

    @Override
    public void register(RegisterBody registerBody) {
        authFeignClient.registerUrl(registerBody);
    }
}
