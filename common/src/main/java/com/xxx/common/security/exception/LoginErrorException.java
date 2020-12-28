package com.xxx.common.security.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginErrorException extends AuthenticationException {

    public LoginErrorException(){
        super("登录异常");
    }

}
