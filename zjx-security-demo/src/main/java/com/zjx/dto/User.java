package com.zjx.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.zjx.validator.MyConstraint;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @Author: zjx
 * @Date: 2018/9/19 16:03
 */
public class User {
    public interface UserSimpleView {
    }

    public interface UserdetailView extends UserSimpleView {
    }

    @JsonView({UserSimpleView.class})
    @MyConstraint(message = "这是一个测试")
    private String username;

    @NotBlank(message = "密码不能为空")
    @JsonView(UserdetailView.class)
    private String password;
    @JsonView({UserSimpleView.class})
    private String id;
    @JsonView({UserSimpleView.class})
    @Past(message = "生日必须是过去的时间")
    private Date birthday;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
