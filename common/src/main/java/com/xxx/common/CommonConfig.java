package com.xxx.common;

import com.xxx.common.rest.config.RestConfig;
import com.xxx.common.security.IgnoreSecurityConfig;
import com.xxx.common.thead.ThreadConfig;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * @author 86130
 */
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Import({
        RestConfig.class,
        IgnoreSecurityConfig.class,
        ThreadConfig.class
})
public class CommonConfig {

}
