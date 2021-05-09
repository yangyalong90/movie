package com.xxx.movie.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class ZuulServerApp {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApp.class, args);
        System.out.println("zuul server success");
    }

}
