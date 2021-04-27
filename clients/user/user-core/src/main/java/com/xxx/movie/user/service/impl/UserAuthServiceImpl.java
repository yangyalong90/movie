package com.xxx.movie.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.common.security.detail.UserDetail;
import com.xxx.common.security.service.AbstractUserDetailService;
import com.xxx.movie.user.common.entity.UserInfo;
import com.xxx.movie.user.dao.UserMapper;
import com.xxx.movie.user.service.UserAuthService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 86130
 */
@Service
public class UserAuthServiceImpl implements UserAuthService, AbstractUserDetailService {

    private UserMapper userMapper;

    public UserAuthServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean permission(String token, String url) {
        return false;
    }

    @Override
    public UserDetail loadUserDetail(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userMapper.selectOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUsername, username));

        if (userInfo == null) {
            throw new UsernameNotFoundException("cant found user");
        }

        return new UserDetail(userInfo.getUsername(), userInfo.getPassword());
    }
}
