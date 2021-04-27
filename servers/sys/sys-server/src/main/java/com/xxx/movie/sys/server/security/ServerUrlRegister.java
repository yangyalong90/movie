package com.xxx.movie.sys.server.security;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxx.common.security.handler.url.SecurityUrlRegister;
import com.xxx.movie.sys.server.dao.UrlMapper;
import com.xxx.movie.sys.server.entity.UrlEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ServerUrlRegister extends SecurityUrlRegister {

    private UrlMapper urlMapper;

    public ServerUrlRegister(UrlMapper urlMapper) {
        this.urlMapper = urlMapper;
    }

    @Override
    public void register(RegisterBody registerBody) {
        // do nothing
        String appId = registerBody.getAppId();
        urlMapper.delete(new UpdateWrapper<UrlEntity>().lambda().eq(UrlEntity::getAppId, appId));
        registerBody.getUrls().forEach(url -> {
            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setAppId(appId);
            urlEntity.setUrl(url.getUrl());
            urlEntity.setIsIgnore(url.getIgnore());
            urlMapper.insert(urlEntity);
        });

    }
}
