package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.mapper.GoodsOrderMapper;
import com.jinyang.resthome.mapper.OrdersMapper;
import com.jinyang.resthome.pojo.GoodsOrder;
import com.jinyang.resthome.pojo.Orders;
import com.jinyang.resthome.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【order】的数据库操作Service实现
 * @createDate 2025-02-25 23:38:32
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    @Override
    public Result insertOrder(Orders order, List<GoodsOrder> goodsOrders) {
        ordersMapper.insert(order);
        Long orderId = order.getOid();

        // 保存订单商品关联信息
        for (GoodsOrder goodsOrder : goodsOrders) {
            goodsOrder.setOid(orderId);
            goodsOrderMapper.insert(goodsOrder);
        }
        return Result.ok(null);
    }
}




