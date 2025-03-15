package com.jinyang.resthome.pojo.dto;

import com.jinyang.resthome.pojo.Dishesorder;
import com.jinyang.resthome.pojo.GoodsOrder;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @ClassName: OrderRequest
 * @Author: jinyang
 * @Date: 2025/2/26 18:19
 * @Version: 1.0
 */
@Data
public class DishesOrderRequest {
    private Long uid;
    private List<Dishesorder> dishesorders;
}
