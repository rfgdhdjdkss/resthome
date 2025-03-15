package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description:
 * @ClassName: DeleteDishCartRequest
 * @Author: jinyang
 * @Date: 2025/3/15 17:54
 * @Version: 1.0
 */
@Data
public class DeleteDishCartRequest {
    private Long dishId;
    private Long uid;
}
