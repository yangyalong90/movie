package com.xxx.common.security.annotations;

import com.xxx.common.security.SecurityConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SecurityConfig.class)
public @interface EnableSecurity {
}
