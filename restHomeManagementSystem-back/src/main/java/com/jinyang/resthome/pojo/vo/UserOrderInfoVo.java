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
public class UserOrderInfoVo {
    private Long gid;
    private Long uid;
    private String title;
    private String image;
    private Double price;
    private Integer quantity;
    private String fee;

    public UserOrderInfoVo(Long gid, Long uid, String title, String image, Double price, Integer quantity, String fee) {
        this.gid = gid;
        this.uid = uid;
        this.title = title;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.fee = fee;
    }
}
