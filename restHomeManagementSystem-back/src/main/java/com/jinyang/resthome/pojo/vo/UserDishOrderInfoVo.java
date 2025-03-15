package com.jinyang.resthome.pojo.vo;

import lombok.Data;

/**
 * @Description:
 * @ClassName: UserOrderInfoV0
 * @Author: jinyang
 * @Date: 2025/2/26 14:36
 * @Version: 1.0
 */
@Data
public class UserDishOrderInfoVo {
    private Long dishId;
    private Long uid;
    private String dishName;
    private String dishImg;
    private Double dishPrice;
    private Integer quantity;

    public UserDishOrderInfoVo(Long dishId, Long uid, String dishName, String dishImg, Double dishPrice, Integer quantity) {
        this.dishId = dishId;
        this.uid = uid;
        this.dishName = dishName;
        this.dishImg = dishImg;
        this.dishPrice = dishPrice;
        this.quantity = quantity;
    }
}
