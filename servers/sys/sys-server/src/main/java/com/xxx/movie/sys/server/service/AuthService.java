package com.xxx.movie.sys.server.service;

import com.xxx.common.register.AppRegister;

public interface AuthService {

    boolean permission(String token, String appId, String url);

    void register(AppRegister.RegisterBody registerBody);

}
