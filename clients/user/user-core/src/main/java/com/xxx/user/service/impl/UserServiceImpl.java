package com.xxx.user.service.impl;

import com.xxx.user.common.entity.UserInfo;
import com.xxx.user.dao.UserMapper;
import com.xxx.user.service.UserService;
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
