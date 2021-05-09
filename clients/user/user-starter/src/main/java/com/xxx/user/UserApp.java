package com.xxx.user;

import com.xxx.common.mybatis.EnableMybatis;
import com.xxx.common.security.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMybatis
@EnableSecurity
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
        System.out.println("user run success");
    }

}
