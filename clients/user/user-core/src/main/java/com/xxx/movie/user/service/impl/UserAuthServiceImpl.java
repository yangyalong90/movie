package com.xxx.movie.user.service.impl;

import com.xxx.movie.user.service.UserAuthService;

public class UserAuthServiceImpl implements UserAuthService {
    @Override
    public boolean permission(String token, String url) {
        return false;
    }
}
