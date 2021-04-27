package com.xxx.movie.user.controller;

import com.xxx.common.security.detail.UserAuthority;
import com.xxx.common.security.detail.UserDetail;
import com.xxx.movie.user.common.model.auth.UrlResource;
import com.xxx.movie.user.service.UserAuthService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/permission")
    public boolean permission(@RequestParam("token") String token,
                              @RequestParam("url") String url) {

        return userAuthService.permission(token, url);

    }

    @GetMapping("/ignore/urls")
    public List<UrlResource> ignoreUrls(@RequestParam(value = "application", required = false) String application) {

        UrlResource resource = new UrlResource();
        resource.setApplication("user");
        resource.setUrls(Collections.singletonList("/user/info/{id}"));
        return Collections.singletonList(resource);

    }

    @PostMapping("/details")
    public UserDetail details(@RequestParam("username") String username) {
        UserDetail userDetail = new UserDetail(username, "1");
        List<UserAuthority> userAuthorities = new ArrayList<>();
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setAuthority("a");
        userDetail.setAuthorities(userAuthorities);
        return userDetail;
    }

}
