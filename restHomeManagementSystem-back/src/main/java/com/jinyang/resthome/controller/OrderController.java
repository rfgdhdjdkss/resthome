package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.GoodsOrder;
import com.jinyang.resthome.pojo.Orders;
import com.jinyang.resthome.pojo.dto.OrderRequest;
import com.jinyang.resthome.pojo.dto.UpdateOrderStatusRequest;
import com.jinyang.resthome.service.OrderService;
import com.jinyang.resthome.util.OrderNumberGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Date createTime = new Date();
        order.setCreateTime(createTime);
        order.setOrderNumber("PAY_"+OrderNumberGeneratorUtils.generateOrderNumber(order.getCreateTime(), request.getUid()));
        order.setUid(request.getUid());
        order.setOrderStatus("pending");
        List<GoodsOrder> goodsOrders = request.getGoodsOrders();
        Result result = orderService.insertOrder(order, goodsOrders);
        return result;
    }

    @GetMapping("/getOrderInfoByOid/{oid}")
    public Result getOrderInfoByOid(@PathVariable("oid") Long oid) {
        Result result = orderService.selectOrderByOid(oid);
        return result;
    }

    @PutMapping("/updateOrderStatus/{oid}")
    public Result updateOrderStatus(@PathVariable Long oid, @RequestBody UpdateOrderStatusRequest request) {
        Result result = orderService.updateOrderStatusByOid(oid, request.getOrderStatus());
        return result;
    }
    @GetMapping("/getOrdersByUid/{uid}")
    public Result getOrdersByUid(@PathVariable("uid") Long uid) {
        Result result= orderService.selectOrdersListByUid(uid);
        return result;
    }
}
