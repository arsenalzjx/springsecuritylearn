package com.zjx.service.impl;

import com.zjx.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * <p>@ClassName: HelloServiceImpl </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/26 15:17</p>
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        System.out.println("greeting");
        return "hello"+name;
    }
}
