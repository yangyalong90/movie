package com.xxx.user.export.feign;

import com.xxx.common.security.detail.UserDetail;
import com.xxx.user.common.entity.UserInfo;
import com.xxx.user.common.model.auth.UrlResource;
import com.xxx.user.export.feign.hystrix.UserFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user", fallbackFactory = UserFeignFallbackFactory.class)
public interface UserFeignClient {

    @GetMapping("/user/info/{id}")
    UserInfo queryById(@PathVariable("id") Long id);

    @GetMapping("/user/auth/permission")
    boolean permission(@RequestParam("token") String token, @RequestParam("url") String url);

    @PostMapping("/user/auth/details")
    UserDetail authDetails(@RequestParam("username") String username);

    @GetMapping("/user/auth/ignore/urls")
    List<UrlResource> ignoreUrls(@RequestParam(value = "application", required = false) String application);

}
