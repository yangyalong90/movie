package com.xxx.movie.sys.server.controller;

import com.xxx.common.security.handler.url.SecurityUrlRegister;
import com.xxx.movie.sys.server.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/permission")
    public boolean permission(@RequestParam("token") String token,
                              @RequestParam("url") String url) {

        return authService.permission(token, url);
    }

    @PostMapping("/register")
    public void register(@RequestBody SecurityUrlRegister.RegisterBody registerBody) {

        authService.register(registerBody);
    }

}
