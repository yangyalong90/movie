package com.xxx.movie.user.export.feign.hystrix;

import com.xxx.movie.user.export.dto.UserInfo;
import com.xxx.movie.user.export.feign.UserFeignClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

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

    class UserClientFallback implements UserFeignClient {

        Throwable cause;

        public UserClientFallback(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public UserInfo queryById(Long id) {

            System.out.println(cause.getMessage());

            return ERROR_USER;
        }
    }
}
