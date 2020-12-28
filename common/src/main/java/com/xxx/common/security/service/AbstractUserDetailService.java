package com.xxx.common.security.service;

import com.xxx.common.security.detail.UserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class AbstractUserDetailService implements UserDetailsService {
    @Override
    public final UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserDetail(username);
    }

    public abstract UserDetail loadUserDetail(String username) throws UsernameNotFoundException;

}
