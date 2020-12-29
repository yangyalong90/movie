package com.xxx.movie.zuul.security.service;

import com.xxx.common.security.detail.UserDetail;
import com.xxx.common.security.exception.LoginErrorException;
import com.xxx.common.security.service.AbstractUserDetailService;
import com.xxx.movie.user.export.feign.UserFeignClient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ZuulUserDetailService extends AbstractUserDetailService {

    private UserFeignClient userFeignClient;

    public ZuulUserDetailService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public UserDetail loadUserDetail(String username) throws UsernameNotFoundException {
        UserDetail userDetail;
        try {
            userDetail = userFeignClient.authDetails(username);
        } catch (Exception e) {
            throw new LoginErrorException();
        }
        if (userDetail == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return userDetail;
    }
}
