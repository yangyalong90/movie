package com.xxx.common.mybatis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MybatisConfig.class)
public @interface EnableMybatis {
}
