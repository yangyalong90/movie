package com.xxx.common.security.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("auth")
public interface SecurityFeignClient {

    @GetMapping("/permission")
    boolean permission(@RequestParam("token") String token, @RequestParam("url") String url);

    @PostMapping("/register")
    void register();

}
