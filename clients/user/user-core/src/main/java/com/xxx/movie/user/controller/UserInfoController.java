package com.xxx.movie.user.controller;

import com.xxx.common.model.ApiResult;
import com.xxx.common.version.annotations.ApiVersion;
import com.xxx.movie.user.common.entity.UserInfo;
import com.xxx.movie.user.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/info")
public class UserInfoController {

    private UserService userService;

    private RedisTemplate redisTemplate;

    public UserInfoController(UserService userService, RedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @ApiVersion("0.3")
    @GetMapping("/{id}")
    public UserInfo queryById(@PathVariable("id") Long id){
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setName("0.3");
        return info;
    }

    @GetMapping("/demo/redis")
    public ApiResult redis(@RequestParam("script") String script){
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript(script, String.class);
        Object execute = redisTemplate.execute(defaultRedisScript, Collections.emptyList());

        return ApiResult.error();
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
