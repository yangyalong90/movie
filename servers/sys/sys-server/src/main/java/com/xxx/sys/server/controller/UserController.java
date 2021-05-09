package com.xxx.sys.server.controller;

import com.xxx.common.model.ApiResult;
import com.xxx.common.security.jwt.JWTToken;
import com.xxx.sys.server.model.UserInfo;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public ApiResult info(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        JWTToken token = (JWTToken) securityContext.getAuthentication();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(token.getPrincipal().getUsername());
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("admin");
        userInfo.setPermissions(permissions);
        return ApiResult.success(userInfo);
    }

}
