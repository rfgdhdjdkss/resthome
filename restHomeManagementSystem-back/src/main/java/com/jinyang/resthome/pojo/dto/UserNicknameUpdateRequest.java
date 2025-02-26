package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description: 用户修改昵称请求类
 * @ClassName: UserPhoneUpdateRequest
 * @Author: jinyang
 * @Date: 2025/2/21 16:35
 * @Version: 1.0
 */
@Data
public class UserNicknameUpdateRequest {
    private String nickname;
    private Long uid;

    @Override
    public String toString() {
        return "UserNicknameUpdateRequest{" +
                "nickname='" + nickname + '\'' +
                ", uid=" + uid +
                '}';
    }
}
