package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description: 加入购物车时请求数据的DTO类
 * @ClassName: AddNewCartRequest
 * @Author: jinyang
 * @Date: 2025/2/23 21:25
 * @Version: 1.0
 */
@Data
public class AddNewCartRequest {
    private Long uid;
    private Long gid;
    private Long quantity;

    @Override
    public String toString() {
        return "AddNewCartRequest{" +
                "uid=" + uid +
                ", gid=" + gid +
                ", quantity=" + quantity +
                '}';
    }
}
