package com.xxx.common.feign;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.xxx.**.feign.**")
public class FeignScanConfig {
}
