package com.xxx.movie.zuul;

import com.xxx.common.feign.EnableFeignScan;
import com.xxx.common.security.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableFeignScan
@EnableSecurity
public class ZuulServerApp {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApp.class, args);
        System.out.println("zuul server success");
    }

}
