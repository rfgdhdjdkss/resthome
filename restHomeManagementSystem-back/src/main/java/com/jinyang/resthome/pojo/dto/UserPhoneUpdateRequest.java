package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description: 用户修改手机号请求类
 * @ClassName: UserPhoneUpdateRequest
 * @Author: jinyang
 * @Date: 2025/2/21 16:35
 * @Version: 1.0
 */
@Data
public class UserPhoneUpdateRequest {
    private String phone;
    private Long uid;

    @Override
    public String toString() {
        return "UserPhoneUpdateRequest{" +
                "phone='" + phone + '\'' +
                ", uid=" + uid +
                '}';
    }
}
