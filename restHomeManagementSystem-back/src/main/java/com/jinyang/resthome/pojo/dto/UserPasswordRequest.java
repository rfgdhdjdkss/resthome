package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description: 修改密码时用来接收请求数据的DTO类
 * @ClassName: UserPasswordRequest
 * @Author: jinyang
 * @Date: 2024/11/3 16:24
 * @Version: 1.0
 */
@Data
public class UserPasswordRequest {
    private Long uid;
    private String originalPassword;
    private String newPassword1;
    private String newPassword2;

}
