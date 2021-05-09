package com.xxx.sys.server.controller;

import com.xxx.common.security.handler.url.IgnoreSecurityUrl;
import com.xxx.common.register.AppRegister;
import com.xxx.sys.server.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/permission")
    @IgnoreSecurityUrl
    public boolean permission(@RequestParam(value = "token", required = false) String token,
                              @RequestParam("appId") String appId,
                              @RequestParam("url") String url) {

        return authService.permission(token, appId, url);
    }

    @PostMapping("/register")
    @IgnoreSecurityUrl
    public void register(@RequestBody AppRegister.RegisterBody registerBody) {

        authService.register(registerBody);
    }

}
