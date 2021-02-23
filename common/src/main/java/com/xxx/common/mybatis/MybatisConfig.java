package com.xxx.common.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xxx.movie.**.dao.**")
// 切面扫描
@ComponentScan("com.xxx.common.mybatis.aspect")
public class MybatisConfig {
}
