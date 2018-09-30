package com.zjx.exception;

/**
 * <p>@ClassName: UserNotExistException </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/26 18:25</p>
 */
public class UserNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1571800560779840370L;

    private String id;

    public UserNotExistException(String id) {
        super("user not exist");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
