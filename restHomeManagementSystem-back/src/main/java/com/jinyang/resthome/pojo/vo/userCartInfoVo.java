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
public class userCartInfoVo {
    private Long uid;
    private Long gid;
    private String image;
    private String title;
    private Double price;
    private Integer quantity;

    public userCartInfoVo(Long uid, Long gid, String image, String title, Double price, Integer quantity) {
        this.uid = uid;
        this.gid = gid;
        this.image = image;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
}
