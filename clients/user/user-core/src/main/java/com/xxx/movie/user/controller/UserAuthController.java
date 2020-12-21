package com.xxx.movie.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @GetMapping("/permission")
    public boolean permission(@RequestParam("token") String token,
                              @RequestParam("url") String url) {

        return true;

    }

}
