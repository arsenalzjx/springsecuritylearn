package com.zjx.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>@ClassName: UserQueryCondition </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/19 16:18</p>
 */
public class UserQueryCondition {
    private String username;

    @ApiModelProperty(value = "用户年龄起始值")
    private Integer age;

    @ApiModelProperty(value = "用户年龄终止值")
    private Integer ageTo;
    
    private String xxx;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
    }

    public String getXxx() {
        return xxx;
    }

    public void setXxx(String xxx) {
        this.xxx = xxx;
    }
}
