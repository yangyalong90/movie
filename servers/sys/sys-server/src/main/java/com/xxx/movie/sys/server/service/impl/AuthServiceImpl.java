package com.xxx.movie.sys.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.common.security.detail.UserDetail;
import com.xxx.common.security.handler.url.SecurityUrlRegister;
import com.xxx.common.security.service.AbstractUserDetailService;
import com.xxx.movie.sys.server.dao.UrlMapper;
import com.xxx.movie.sys.server.dao.UserMapper;
import com.xxx.movie.sys.server.entity.UrlEntity;
import com.xxx.movie.sys.server.entity.UserEntity;
import com.xxx.movie.sys.server.security.ServerUrlRegister;
import com.xxx.movie.sys.server.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService, AbstractUserDetailService {

    private PathMatcher pathMatcher = new AntPathMatcher();

    private UserMapper userMapper;
    private UrlMapper urlMapper;

    private ServerUrlRegister urlRegister;

    public AuthServiceImpl(UserMapper userMapper, UrlMapper urlMapper, ServerUrlRegister urlRegister) {
        this.userMapper = userMapper;
        this.urlMapper = urlMapper;
        this.urlRegister = urlRegister;
    }

    @Override
    public boolean permission(String token, String appId, String url) {

        List<UrlEntity> ignoreUrls = urlMapper.selectList(new QueryWrapper<UrlEntity>().lambda()
                .eq(UrlEntity::getAppId, appId)
                .eq(UrlEntity::getIsIgnore, "1"));

        boolean match = ignoreUrls.stream().anyMatch(
                urlEntity -> pathMatcher.match(urlEntity.getUrl().split(SecurityUrlRegister.SIGN)[1], url)
        );
        if (match) {
            return true;
        }

        return false;
    }

    @Override
    public void register(SecurityUrlRegister.RegisterBody registerBody) {
        urlRegister.register(registerBody);
    }

    @Override
    public UserDetail loadUserDetail(String username) throws UsernameNotFoundException {
        UserEntity userInfo = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getUsername, username));

        if (userInfo == null) {
            throw new UsernameNotFoundException("cant found user");
        }

        return new UserDetail(userInfo.getUsername(), userInfo.getPassword());
    }
}
