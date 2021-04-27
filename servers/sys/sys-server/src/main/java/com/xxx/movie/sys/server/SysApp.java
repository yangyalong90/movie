package com.xxx.movie.sys.server;

import com.xxx.common.mybatis.EnableMybatis;
import com.xxx.common.security.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMybatis
@EnableSecurity
public class SysApp {

    public static void main(String[] args) {
        SpringApplication.run(SysApp.class, args);
        System.out.println("sys run success");
    }

}
