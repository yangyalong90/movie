package com.xxx.movie.sys.client.feign;

import com.xxx.common.security.handler.url.SecurityUrlRegister;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("sys")
public interface AuthFeignClient {

    @PostMapping("/register")
    void registerUrl(@RequestBody SecurityUrlRegister.RegisterBody registerBody);

    @GetMapping("/permission")
    boolean permission(@RequestParam("token") String token,
                       @RequestParam("appId") String appId,
                       @RequestParam("url") String url);
}
