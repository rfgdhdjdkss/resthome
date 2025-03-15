package com.jinyang.resthome.pojo.vo;

import lombok.Data;

/**
 * @Description:
 * @ClassName: userCartInfoVo
 * @Author: jinyang
 * @Date: 2025/2/24 17:02
 * @Version: 1.0
 */
@Data
public class userDishCartInfoVo {
    private Long uid;
    private Long dishId;
    private String dishImg;
    private String dishName;
    private Double dishPrice;
    private Integer quantity;

    public userDishCartInfoVo(Long uid, Long dishId, String dishImg, String dishName, Double dishPrice, Integer quantity) {
        this.uid = uid;
        this.dishId = dishId;
        this.dishImg = dishImg;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.quantity = quantity;
    }
}
