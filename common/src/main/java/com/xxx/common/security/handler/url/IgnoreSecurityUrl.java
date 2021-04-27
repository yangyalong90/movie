package com.xxx.common.security.handler.url;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface IgnoreSecurityUrl {
}
