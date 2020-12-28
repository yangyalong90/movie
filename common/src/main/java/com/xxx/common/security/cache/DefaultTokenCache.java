package com.xxx.common.security.cache;

import com.xxx.common.security.detail.UserDetail;

import java.util.HashMap;
import java.util.Map;

public class DefaultTokenCache implements TokenCache {

    private Map<String, UserDetail> userDetailMap = new HashMap<>();

    @Override
    public void put(String token, UserDetail detail) {
        userDetailMap.put(token, detail);
    }

    @Override
    public UserDetail get(String token) {
        return userDetailMap.get(token);
    }
}
