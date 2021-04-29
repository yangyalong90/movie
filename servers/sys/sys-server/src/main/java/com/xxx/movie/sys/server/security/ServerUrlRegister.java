package com.xxx.movie.sys.server.security;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxx.common.security.handler.url.SecurityUrlRegister;
import com.xxx.movie.sys.server.dao.UrlMapper;
import com.xxx.movie.sys.server.entity.UrlEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class ServerUrlRegister extends SecurityUrlRegister {

    private UrlMapper urlMapper;

    public ServerUrlRegister(UrlMapper urlMapper) {
        this.urlMapper = urlMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void register(RegisterBody registerBody) {
        String appId = registerBody.getAppId();
        urlMapper.delete(new UpdateWrapper<UrlEntity>().lambda().eq(UrlEntity::getAppId, appId));
        urlMapper.batchInsert(registerBody.getUrls().stream().map(url -> {
            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setAppId(appId);
            urlEntity.setUrl(url.getUrl());
            urlEntity.setIsIgnore(url.getIgnore());
            return urlEntity;
        }).collect(Collectors.toList()));

    }
}
