package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.GoodsOrder;
import com.jinyang.resthome.pojo.Orders;
import com.jinyang.resthome.pojo.dto.OrderRequest;
import com.jinyang.resthome.service.OrderService;
import com.jinyang.resthome.util.OrderNumberGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @ClassName: OrderController
 * @Author: jinyang
 * @Date: 2025/2/26 17:58
 * @Version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody OrderRequest request) {
        System.out.println(request);
        Orders order = new Orders();
        Date date = new Date();
        order.setCreateTime(LocalDateTime.now());
        order.setOrderNumber(OrderNumberGeneratorUtils.generateOrderNumber(order.getCreateTime(), order.getUid()));
        order.setUid(request.getUid());
        order.setOrderStatus("pending");
        List<GoodsOrder> goodsOrders = request.getGoodsOrders();
        Result result = orderService.insertOrder(order, goodsOrders);
        return null;
    }
}
