package com.xxx.common.security.service;

import com.xxx.common.security.detail.UserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AbstractUserDetailService extends UserDetailsService {

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserDetail(username);
    }

    UserDetail loadUserDetail(String username) throws UsernameNotFoundException;

}
