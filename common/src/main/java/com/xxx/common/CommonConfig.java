package com.xxx.common;

import com.xxx.common.rest.config.RestConfig;
import com.xxx.common.security.IgnoreSecurityConfig;
import com.xxx.common.version.WebMvcSupportConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        RestConfig.class,
        WebMvcSupportConfig.class,
        IgnoreSecurityConfig.class
})
public class CommonConfig {

}
