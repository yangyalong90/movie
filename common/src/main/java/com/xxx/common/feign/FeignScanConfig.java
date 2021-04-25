package com.xxx.common.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.xxx.**.feign.**")
// 配置 hystrix component 扫描
@ComponentScan("com.xxx.**.feign.hystrix.**")
public class FeignScanConfig {
}
