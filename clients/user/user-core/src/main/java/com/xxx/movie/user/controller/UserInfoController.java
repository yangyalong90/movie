package com.xxx.movie.user.controller;

import com.xxx.common.rest.annotations.ApiVersion;
import com.xxx.movie.user.common.entity.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class UserInfoController {

    @ApiVersion("0.3")
    @GetMapping("/{id}")
    public UserInfo queryById(@PathVariable("id") Long id){
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setName("0.3");
        return info;
    }

    @ApiVersion("0.1")
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

}
