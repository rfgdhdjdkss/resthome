package com.jinyang.resthome.common;

/**
 * @Description: 统一返回结果状态信息类
 * @ClassName: ResultCodeEnum
 * @Author: jinyang
 * @Date: 2024/10/3 16:50
 * @Version: 1.0
 */

public enum ResultCodeEnum {

    SUCCESS(200, "success"),
    USERNAME_ERROR(501, "usernameError"),
    PASSWORD_ERROR(503, "passwordError"),
    NOTLOGIN(504, "notLogin"),
    USERNAME_USED(505, "用户名被占用"),
    USERNAME_NOT_FOUND(506, "用户名未找到"),
    PHONE_USED(507, "手机号已注册，可使用手机号登录"),
    PHONE_OCCUPIED(508, "手机号被占用,不可绑定"),
    UPDATE_ERROR(509, "修改失败"),
    SELECT_EMPTY(510, "查询为空"),
    INSERT_FAIL(511, "创建失败"),
    PHONE_ILLEGAL(512, "手机号不合法，请输入11位手机号"),
    DELETE_ERROR(513, "删除失败");
    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
