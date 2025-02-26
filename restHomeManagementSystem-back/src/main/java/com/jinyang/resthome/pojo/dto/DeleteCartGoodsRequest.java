package com.jinyang.resthome.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @ClassName: deleteCartGoodsRequest
 * @Author: jinyang
 * @Date: 2025/2/24 18:43
 * @Version: 1.0
 */
@Data
public class DeleteCartGoodsRequest {
    private List<Long> gids;
    private Long uid;
}
