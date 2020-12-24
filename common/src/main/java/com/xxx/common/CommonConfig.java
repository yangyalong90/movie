package com.xxx.common;

import com.xxx.common.rest.config.RestConfig;
import com.xxx.common.version.WebMvcSupportConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RestConfig.class, WebMvcSupportConfig.class})
public class CommonConfig {

}
