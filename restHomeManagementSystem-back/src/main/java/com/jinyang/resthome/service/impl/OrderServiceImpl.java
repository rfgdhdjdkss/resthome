package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.mapper.GoodsMapper;
import com.jinyang.resthome.mapper.GoodsOrderMapper;
import com.jinyang.resthome.mapper.OrdersMapper;
import com.jinyang.resthome.pojo.*;
import com.jinyang.resthome.pojo.vo.AllOrderVo;
import com.jinyang.resthome.pojo.vo.OrderGoodsInfo;
import com.jinyang.resthome.pojo.vo.SubmitOrderVo;
import com.jinyang.resthome.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Result insertOrder(Orders order, List<GoodsOrder> goodsOrders) {
        ordersMapper.insert(order);
        Long orderId = order.getOid();

        // 保存订单商品关联信息
        for (GoodsOrder goodsOrder : goodsOrders) {
            goodsOrder.setOid(orderId);
            goodsOrderMapper.insert(goodsOrder);
        }
        return Result.ok(orderId);
    }

    @Override
    public Result selectOrderByOid(Long oid) {
        // 创建查询条件，根据订单 ID 查询商品订单关联信息
        QueryWrapper<GoodsOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("oid", oid);

        // 根据订单 ID 查询订单信息
        Orders orders = ordersMapper.selectById(oid);

        // 检查订单状态是否为待处理
        if (orders.getOrderStatus().equals("pending")) {
            // 根据订单 ID 查询商品订单关联信息列表
            List<GoodsOrder> goodsOrderList = goodsOrderMapper.selectList(queryWrapper);

            // 从商品订单关联信息列表中提取商品 ID 列表
            List<Long> gidList = goodsOrderList.stream()
                    .map(GoodsOrder::getGid)
                    .collect(Collectors.toList());

            // 根据商品 ID 列表批量查询商品信息
            List<Goods> goodsList = goodsMapper.selectBatchIds(gidList);

            // 将商品信息列表转换为以商品 ID 为键的 Map，方便后续查找
            Map<Long, Goods> goodsMap = goodsList.stream()
                    .collect(Collectors.toMap(Goods::getGid, goods -> goods));

            // 将商品订单关联信息列表转换为以商品订单 ID 为键的 Map，方便后续查找
            Map<Long, GoodsOrder> goodsOrderMap = goodsOrderList.stream()
                    .collect(Collectors.toMap(GoodsOrder::getGoodsOrderid, goodsOrder -> goodsOrder));

            // 存储订单商品详细信息的列表
            List<OrderGoodsInfo> goodsInfoList = new ArrayList<>();

            // 遍历 goodsOrderMap 中的每个商品订单关联信息
            for (GoodsOrder goodsOrder : goodsOrderMap.values()) {
                // 获取商品 ID
                Long gid = goodsOrder.getGid();
                // 根据商品 ID 从商品信息 Map 中查找商品信息
                Goods goods = goodsMap.get(gid);
                if (goods != null) {
                    // 创建订单商品详细信息对象，包含商品 ID、商品数量、商品标题和商品价格
                    OrderGoodsInfo orderGoodsInfo = new OrderGoodsInfo(
                            goods.getGid(),
                            goodsOrder.getQuantity(),
                            goods.getTitle(),
                            goods.getPrice() // 从 goods 对象中获取商品价格
                    );
                    // 将订单商品详细信息对象添加到列表中
                    goodsInfoList.add(orderGoodsInfo);
                }
            }

            // 创建提交订单视图对象，包含订单 ID、用户 ID、订单编号、订单状态、创建时间和订单商品详细信息列表
            SubmitOrderVo submitOrderVo = new SubmitOrderVo(
                    oid,
                    orders.getUid(),
                    orders.getOrderNumber(),
                    orders.getOrderStatus(),
                    orders.getCreateTime(),
                    goodsInfoList
            );

            // 返回成功结果，包含提交订单视图对象
            return Result.ok(submitOrderVo);
        }

        // 如果订单状态不是待处理，返回查询为空的结果
        return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
    }

    @Override
    public Result updateOrderStatusByOid(Long oid, String orderStatus) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("oid", oid);
        updateWrapper.set("orderStatus", orderStatus);
        int update = ordersMapper.update(updateWrapper);
        if (update != 1) {
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }
        return Result.ok(update);
    }

    @Override
    public Result selectOrdersListByUid(Long uid) {
        // 查询订单信息
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("uid", uid);
        List<Orders> ordersList = ordersMapper.selectList(ordersQueryWrapper);

        // 判断订单是否超时并更新状态
        long currentTime = System.currentTimeMillis();
        long fifteenMinutes = 15 * 60 * 1000; // 15 分钟的毫秒数
        for (Orders order : ordersList) {
            Date createTime = order.getCreateTime();
            if (createTime != null && "pending".equals(order.getOrderStatus())) { // 确保订单状态为待支付（pending）
                long elapsedTime = currentTime - createTime.getTime();
                if (elapsedTime > fifteenMinutes) {
                    // 更新订单状态为 cancelled
                    UpdateWrapper updateWrapper = new UpdateWrapper();
                    updateWrapper.eq("oid", order.getOid());
                    updateWrapper.set("orderStatus", "cancelled");
                    int update = ordersMapper.update(updateWrapper);
                    if (update == 1) {
                        order.setOrderStatus("cancelled");
                    }
                }
            }
        }

        // 获取订单信息的 oid 并转换成列表
        List<Long> oidList = ordersList.stream().map(Orders::getOid).collect(Collectors.toList());

        // 根据 oidList 查询商品订单关联表信息
        QueryWrapper<GoodsOrder> goodsOrderQueryWrapper = new QueryWrapper<>();
        goodsOrderQueryWrapper.in("oid", oidList);
        List<GoodsOrder> goodsOrderList = goodsOrderMapper.selectList(goodsOrderQueryWrapper);

        // 获取商品订单关联表中的商品 id
        List<Long> gidList = goodsOrderList.stream().map(GoodsOrder::getGid).collect(Collectors.toList());

        // 查询商品信息
        List<Goods> goodsList = goodsMapper.selectBatchIds(gidList);

        // 将商品信息按 gid 存储到 Map 中，方便查找
        Map<Long, Goods> goodsMap = goodsList.stream()
                .collect(Collectors.toMap(Goods::getGid, goods -> goods));

        // 按订单 ID 分组商品订单信息
        Map<Long, List<GoodsOrder>> orderGoodsMap = goodsOrderList.stream()
                .collect(Collectors.groupingBy(GoodsOrder::getOid));

        List<AllOrderVo> result = new ArrayList<>();
        for (Orders order : ordersList) {
            Long oid = order.getOid();
            String orderNumber = order.getOrderNumber();
            String orderStatus = order.getOrderStatus();
            Date createTime = order.getCreateTime();

            List<OrderGoodsInfo> goodsInfoList = new ArrayList<>();
            List<GoodsOrder> orderGoods = orderGoodsMap.getOrDefault(oid, new ArrayList<>());
            for (GoodsOrder goodsOrder : orderGoods) {
                Long gid = goodsOrder.getGid();
                Goods goods = goodsMap.get(gid);
                if (goods != null) {
                    goodsInfoList.add(new OrderGoodsInfo(
                            gid,
                            goodsOrder.getQuantity(),
                            goods.getTitle(),
                            goods.getImage(),
                            goods.getPrice()
                    ));
                }
            }

            result.add(new AllOrderVo(oid, uid, orderNumber, orderStatus, createTime, goodsInfoList));
        }

        return Result.ok(result);
    }
}
