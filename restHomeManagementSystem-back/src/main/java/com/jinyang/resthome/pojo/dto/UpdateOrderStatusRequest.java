package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description: 更新订单状态请求类
 * @ClassName: UpdateOrderStatusRequest
 * @Author: jinyang
 * @Date: 2025/3/2 17:50
 * @Version: 1.0
 */
@Data
public class UpdateOrderStatusRequest {
    private String orderStatus;
}
