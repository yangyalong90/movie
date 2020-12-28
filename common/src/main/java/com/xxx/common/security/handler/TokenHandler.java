package com.xxx.common.security.handler;

import com.xxx.common.security.detail.UserDetail;
import org.springframework.security.core.Authentication;

public interface TokenHandler {

    Authentication token(UserDetail detail);

    Authentication detail(String token);

}
