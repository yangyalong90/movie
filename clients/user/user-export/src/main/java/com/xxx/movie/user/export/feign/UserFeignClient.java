package com.xxx.movie.user.export.feign;

import com.xxx.movie.user.export.dto.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user")
public interface UserFeignClient {

    @GetMapping("/user/feign/{id}")
    UserInfo queryById(@PathVariable("id") Long id);

}
