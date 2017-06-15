package org.manlier.contracts;

/**
 * 用户登录以及注册状态
 * Created by manlier on 2017/6/11.
 */
public enum UserStatus {

    ACCOUNT_NOT_EXIST("账户不存在"),
    PASSWORD_ERROR("密码错误"),
    LOGIN_SUCCESS("登录成功"),
    EMAIL_HAS_EXIST("邮箱已存在"),
    PASSWORD_DOES_NOT_MATCH("密码不匹配");

    private String msg;

    UserStatus(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
