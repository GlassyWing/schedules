package org.manlier.contracts;

/**
 * 用户登录以及注册状态
 * Created by manlier on 2017/6/11.
 */
public enum UserStatus {

    ACCOUNT_NOT_EXIST("email:账户不存在"),
    PASSWORD_ERROR("password:密码错误"),
    EMAIL_HAS_EXIST("email:邮箱已存在");

    private String msg;

    UserStatus(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
