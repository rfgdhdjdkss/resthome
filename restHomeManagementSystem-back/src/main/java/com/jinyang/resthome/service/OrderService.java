package com.jinyang.resthome.service;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Dishesorder;
import com.jinyang.resthome.pojo.GoodsOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinyang.resthome.pojo.Orders;
import com.jinyang.resthome.pojo.dto.OrderRequest;

import java.util.List;

/**
* @author jinyang
* @description 针对表【orders】的数据库操作Service
* @createDate 2025-02-25 23:38:32
*/
public interface OrderService extends IService<Orders> {

    Result insertOrder(Orders order, List<GoodsOrder> goodsOrders);

    Result selectOrderByOid(Long oid);

    Result updateOrderStatusByOid(Long oid, String orderStatus);

    Result selectOrdersListByUid(Long uid);

    Result insertDishOrder(Orders order, List<Dishesorder> dishesorders);
}
