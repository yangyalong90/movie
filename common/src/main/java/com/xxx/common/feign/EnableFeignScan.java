package com.xxx.common.feign;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FeignScanConfig.class)
public @interface EnableFeignScan {
}
