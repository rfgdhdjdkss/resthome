package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description:
 * @ClassName: loginByUsernameRequest
 * @Author: jinyang
 * @Date: 2025/2/7 17:30
 * @Version: 1.0
 */
@Data
public class loginByUsernameRequest {
    private String username;
    private String password;
    private String phone;
    private String code;

    @Override
    public String toString() {
        return "loginByUsernameRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}
