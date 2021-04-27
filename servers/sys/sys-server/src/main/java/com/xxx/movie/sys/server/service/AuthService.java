package com.xxx.movie.sys.server.service;

import com.xxx.common.security.handler.url.SecurityUrlRegister;

public interface AuthService {

    boolean permission(String token, String url);

    void register(SecurityUrlRegister.RegisterBody registerBody);

}
