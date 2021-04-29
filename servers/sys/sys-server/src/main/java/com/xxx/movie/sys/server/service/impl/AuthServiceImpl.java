package com.xxx.movie.sys.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.common.security.cache.TokenCache;
import com.xxx.common.security.detail.UserAuthority;
import com.xxx.common.security.detail.UserDetail;
import com.xxx.common.security.handler.url.SecurityUrlRegister;
import com.xxx.common.security.service.AbstractUserDetailService;
import com.xxx.movie.sys.server.dao.UrlMapper;
import com.xxx.movie.sys.server.dao.UserMapper;
import com.xxx.movie.sys.server.dao.UserUrlMapper;
import com.xxx.movie.sys.server.entity.UrlEntity;
import com.xxx.movie.sys.server.entity.UserEntity;
import com.xxx.movie.sys.server.entity.UserUrlEntity;
import com.xxx.movie.sys.server.security.ServerUrlRegister;
import com.xxx.movie.sys.server.service.AuthService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService, AbstractUserDetailService {

    private PathMatcher pathMatcher = new AntPathMatcher();

    private UserMapper userMapper;
    private UrlMapper urlMapper;
    private UserUrlMapper userUrlMapper;

    private ServerUrlRegister urlRegister;

    private TokenCache tokenCache;

    private List<UrlEntity> ignoreUrls;

    public AuthServiceImpl(UserMapper userMapper, UrlMapper urlMapper, UserUrlMapper userUrlMapper,
                           ServerUrlRegister urlRegister, @Lazy TokenCache tokenCache) {
        this.userMapper = userMapper;
        this.urlMapper = urlMapper;
        this.userUrlMapper = userUrlMapper;
        this.urlRegister = urlRegister;
        this.tokenCache = tokenCache;
    }

    @Override
    public boolean permission(String token, String appId, String url) {

        if (ignoreUrls == null) {
            ignoreUrls = urlMapper.selectList(new QueryWrapper<UrlEntity>().lambda()
                    .eq(UrlEntity::getAppId, appId)
                    .eq(UrlEntity::getIsIgnore, "1"));
        }

        boolean match = ignoreUrls.stream().anyMatch(
                urlEntity -> pathMatcher.match(urlEntity.getUrl().split(SecurityUrlRegister.SIGN)[1], url)
        );
        if (match) {
            return true;
        }
        UserDetail userDetail = tokenCache.get(token);

        return userDetail.getAuthorities().stream().anyMatch(userAuthority -> {
            String authority = userAuthority.getAuthority();
            String[] split = authority.split(SecurityUrlRegister.SIGN);
            return split[0].equals(appId) && pathMatcher.match(split[2], url);
        });

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

        List<UserUrlEntity> userUrlEntities = userUrlMapper.selectList(
                new QueryWrapper<UserUrlEntity>().lambda()
                        .eq(UserUrlEntity::getUsername, username));

        UserDetail userDetail = new UserDetail(username, userInfo.getPassword());
        userDetail.setAuthorities(userUrlEntities.stream().map(userUrlEntity -> {
            UserAuthority userAuthority = new UserAuthority();
            userAuthority.setAuthority(userUrlEntity.getAppId() +
                    SecurityUrlRegister.SIGN +
                    userUrlEntity.getUrl());
            return userAuthority;
        }).collect(Collectors.toList()));
        return userDetail;
    }
}
