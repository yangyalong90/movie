package com.xxx.movie.user.service.impl;

import com.xxx.movie.user.common.entity.UserInfo;
import com.xxx.movie.user.dao.UserMapper;
import com.xxx.movie.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<UserInfo> selectAll() {
        UserInfo userInfo = new UserInfo();
        UserInfo userInfo1 = userMapper.selectById(1);
        return null;
    }
}
