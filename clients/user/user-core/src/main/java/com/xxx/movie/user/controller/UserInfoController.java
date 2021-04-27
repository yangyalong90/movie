package com.xxx.movie.user.controller;

import com.xxx.common.model.ApiResult;
import com.xxx.common.thead.lock.RedisReentrantLock;
import com.xxx.common.version.annotations.ApiVersion;
import com.xxx.movie.user.common.entity.UserInfo;
import com.xxx.movie.user.service.UserService;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/info")
public class UserInfoController {

    private UserService userService;

    private StringRedisTemplate redisTemplate;

    public UserInfoController(UserService userService, StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/redis/demo")
    public ApiResult redis(@RequestParam("script") String script) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Boolean result = valueOperations.setIfAbsent("a", "true", 5, TimeUnit.SECONDS);

        return ApiResult.error();
    }

    @ApiVersion("0.3")
    @GetMapping("/{id}")
    public UserInfo queryById(@PathVariable("id") Long id) {
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setUsername("0.3");
        return info;
    }

    @ApiVersion(value = "0.1", oldFullPath = "/b/{id}")
    @GetMapping("/{id}")
    public UserInfo queryById2(@PathVariable("id") Long id) {
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setUsername("0.1");
        return info;
    }

    @GetMapping("/a/a")
    public UserInfo queryById3() {
        UserInfo info = new UserInfo();
        info.setId(7L);
        info.setUsername("0.13");
        return info;
    }

    @GetMapping("/all")
    public ApiResult selectAll() {
        return ApiResult.success(userService.selectAll());
    }

}
