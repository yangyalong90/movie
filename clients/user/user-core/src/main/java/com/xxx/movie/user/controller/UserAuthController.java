package com.xxx.movie.user.controller;

import com.xxx.common.model.ApiResult;
import com.xxx.common.security.detail.UserAuthority;
import com.xxx.common.security.detail.UserDetail;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @GetMapping("/permission")
    public boolean permission(@RequestParam("token") String token,
                              @RequestParam("url") String url) {

        return true;

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
