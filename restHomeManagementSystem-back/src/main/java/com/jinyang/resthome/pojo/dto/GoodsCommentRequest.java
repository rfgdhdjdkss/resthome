package com.jinyang.resthome.pojo.dto;

/**
 * @Description:
 * @ClassName: GoodsCommentRequest
 * @Author: jinyang
 * @Date: 2025/3/6 15:26
 * @Version: 1.0
 */

import lombok.Data;

@Data
public class GoodsCommentRequest {
    private Long uid; // 用户ID
    private Long gid; // 商品ID
    private String content; // 评价内容
}