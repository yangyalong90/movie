package com.xxx.movie.sys.server;

import com.xxx.common.mybatis.EnableMybatis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMybatis
public class SysApp {

    public static void main(String[] args) {
        SpringApplication.run(SysApp.class, args);
        System.out.println("sys run success");
    }

}
