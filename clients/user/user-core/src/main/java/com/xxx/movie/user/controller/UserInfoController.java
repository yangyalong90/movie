package com.xxx.movie.user.controller;

import com.xxx.common.model.ApiResult;
import com.xxx.common.version.annotations.ApiVersion;
import com.xxx.movie.user.common.entity.UserInfo;
import com.xxx.movie.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class UserInfoController {

    private UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    @ApiVersion("0.3")
    @GetMapping("/{id}")
    public UserInfo queryById(@PathVariable("id") Long id){
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setName("0.3");
        return info;
    }

    @ApiVersion(value = "0.1", oldFullPath = "/b/{id}")
    @GetMapping("/{id}")
    public UserInfo queryById2(@PathVariable("id") Long id){
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setName("0.1");
        return info;
    }

    @GetMapping("/a/a")
    public UserInfo queryById3(){
        UserInfo info = new UserInfo();
        info.setId(7L);
        info.setName("0.13");
        return info;
    }

    @GetMapping("/all")
    public ApiResult selectAll(){
        return ApiResult.success(userService.selectAll());
    }

}
