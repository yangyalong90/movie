package com.xxx.cinema;

import com.xxx.common.feign.EnableFeignScan;
import com.xxx.common.security.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableFeignScan
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableSecurity
public class CinemaApp {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApp.class, args);
        System.out.println("cinema run success");
    }

}
