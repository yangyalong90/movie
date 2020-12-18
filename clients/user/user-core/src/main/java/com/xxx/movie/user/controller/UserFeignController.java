package com.xxx.movie.user.controller;

import com.xxx.movie.user.export.dto.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class UserFeignController {

    @GetMapping("/{id}")
    public UserInfo queryById(@PathVariable("id") Long id){
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setName("e");
        return info;
    }

}
