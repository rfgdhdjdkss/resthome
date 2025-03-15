package com.jinyang.resthome.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description: 根据商品id列表获取商品信息
 * @ClassName: GetGoodsInfoByGidListRequest
 * @Author: jinyang
 * @Date: 2025/2/25 23:43
 * @Version: 1.0
 */
@Data
public class GetDishesInfoByDishIdListRequest {
    private List<Long> DishIdList;
    private Long uid;
}
