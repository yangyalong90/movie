package com.xxx.movie.cinema.controller;

import com.xxx.movie.user.export.dto.UserInfo;
import com.xxx.movie.user.export.feign.UserFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CinemaController {

    private UserFeignClient userFeignClient;

    public CinemaController(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @GetMapping("/demo/{id}")
    public UserInfo demo(@PathVariable("id") Long id){
        return userFeignClient.queryById(id);
    }

}
