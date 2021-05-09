package com.xxx.movie.cinema.controller;

import com.github.pagehelper.PageHelper;
import com.xxx.movie.user.common.entity.UserInfo;
import com.xxx.movie.user.export.feign.UserFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CinemaController {

    @GetMapping("/demo/{id}")
    public UserInfo demo(@PathVariable("id") Long id){
        PageHelper.startPage(1, 1);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        return userInfo;
    }

    @GetMapping("/demo2/{id}")
    public UserInfo demo2(@PathVariable("id") Long id){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        return userInfo;
    }

}
