package com.xxx.user.export.feign.hystrix;

import com.xxx.common.security.detail.UserDetail;
import com.xxx.user.common.entity.UserInfo;
import com.xxx.user.common.model.auth.UrlResource;
import com.xxx.user.export.feign.UserFeignClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFeignFallbackFactory implements FallbackFactory<UserFeignClient> {

    private static final UserInfo ERROR_USER = new UserInfo();
    static {
        ERROR_USER.setId(UserInfo.ERROR_ID);
    }

    @Override
    public UserFeignClient create(Throwable throwable) {

        return new UserClientFallback(throwable);
    }

    static class UserClientFallback implements UserFeignClient {

        Throwable cause;

        public UserClientFallback(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public UserInfo queryById(Long id) {

            System.out.println(cause.getMessage());

            return ERROR_USER;
        }

        @Override
        public boolean permission(String token, String url) {

            System.out.println(cause.getMessage());

            return false;
        }

        @Override
        public UserDetail authDetails(String username) {
            return null;
        }

        @Override
        public List<UrlResource> ignoreUrls(String application) {
            return null;
        }
    }
}
