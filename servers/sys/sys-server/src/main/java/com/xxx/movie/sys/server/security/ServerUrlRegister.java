package com.xxx.movie.sys.server.security;

import com.xxx.common.security.handler.url.SecurityUrlRegister;
import com.xxx.movie.sys.server.dao.UrlMapper;
import com.xxx.movie.sys.server.entity.UrlEntity;
import org.springframework.stereotype.Component;

@Component
public class ServerUrlRegister extends SecurityUrlRegister {

    private UrlMapper urlMapper;

    public ServerUrlRegister(UrlMapper urlMapper) {
        this.urlMapper = urlMapper;
    }

    @Override
    public void register(RegisterBody registerBody) {
        // do nothing
        String application = registerBody.getApplication();
        registerBody.getUrls().forEach(url -> {
            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setApplication(application);
            urlEntity.setUrl(url);
            urlMapper.insert(urlEntity);
        });

    }
}
