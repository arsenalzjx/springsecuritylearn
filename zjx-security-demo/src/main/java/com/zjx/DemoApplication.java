package com.zjx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@ClassName: DemoApplication </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/19 11:32</p>
 */
@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello spring security";
    }
}
