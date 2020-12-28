package com.xxx.common.security.cache;

import com.xxx.common.security.detail.UserDetail;

public interface TokenCache {

    void put(String token, UserDetail detail);

    UserDetail get(String token);
}
