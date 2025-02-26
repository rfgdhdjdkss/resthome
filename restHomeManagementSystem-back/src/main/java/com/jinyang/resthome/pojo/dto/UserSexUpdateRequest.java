package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description: 用户修改性别请求类
 * @ClassName: UserPhoneUpdateRequest
 * @Author: jinyang
 * @Date: 2025/2/21 16:35
 * @Version: 1.0
 */
@Data
public class UserSexUpdateRequest {
    @Override
    public String toString() {
        return "UserSexUpdateRequest{" +
                "sex='" + sex + '\'' +
                ", uid=" + uid +
                '}';
    }

    private String sex;
    private Long uid;

}
