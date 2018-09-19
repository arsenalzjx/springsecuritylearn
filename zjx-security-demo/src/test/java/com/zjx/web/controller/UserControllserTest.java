package com.zjx.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



/**
 * <p>@ClassName: UserControllserTest </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/19 15:07</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllserTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        //伪造mvc环境,使得测试前不必去启动整个项目,加快速度
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        mockMvc.perform( //执行
                MockMvcRequestBuilders.get("/user")//创建请求
                        .param("username","zjx")
                        .param("age","18")
                        .param("ageTo","60")
                        .param("xxx","yyy")
//                        .param("size","15")
//                        .param("page","3")
//                        .param("sort","age,desc")
                        .contentType(MediaType.APPLICATION_JSON_UTF8) //指定
        ).andExpect( //期望
                MockMvcResultMatchers.status().isOk()   //返回结果状态码为200
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(3)//返回json集合长度为3
        );
    }



    @Test
    public void whenGetInfoSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom"));
    }
}
