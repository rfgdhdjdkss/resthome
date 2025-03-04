package com.jinyang.resthome.pojo.vo;

import lombok.Data;

/**
 * @Description: 提交订单：包含商品名称，商品数量，商品id
 * @ClassName: OrderGoodsInfo
 * @Author: jinyang
 * @Date: 2025/3/2 16:25
 * @Version: 1.0
 */
@Data
public class OrderGoodsInfo {
    private Long gid;
    private Integer quantity;
    private String title;
    private String image;
    private Double price;

    public OrderGoodsInfo(Long gid, Integer quantity, String title) {
        this.gid = gid;
        this.quantity = quantity;
        this.title = title;
    }


    public OrderGoodsInfo(Long gid, Integer quantity, String title, String image, Double price) {
        this.gid = gid;
        this.quantity = quantity;
        this.title = title;
        this.image = image;
        this.price = price;
    }

    public OrderGoodsInfo(Long gid, Integer quantity, String title, Double price) {
        this.gid = gid;
        this.quantity = quantity;
        this.title = title;
        this.price = price;
    }
}
