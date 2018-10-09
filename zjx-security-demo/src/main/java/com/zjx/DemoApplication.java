package com.zjx;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>@ClassName: DemoApplication </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/19 11:32</p>
 */
@SpringBootApplication
@RestController
@EnableSwagger2
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello spring security";
    }

    /**
     * @Author: zjx
     * @Description: 输出springboot中组件列表
     * @Date 11:18 2018/10/8
     * @Param [ctx]
     * @return org.springframework.boot.CommandLineRunner
     **/
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx){
        return args -> {
            System.out.println("Spring注册的组件列表:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}
