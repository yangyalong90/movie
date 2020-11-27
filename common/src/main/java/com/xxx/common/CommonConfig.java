package com.xxx.common;

import com.xxx.common.rest.RestConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RestConfig.class)
public class CommonConfig {

}
