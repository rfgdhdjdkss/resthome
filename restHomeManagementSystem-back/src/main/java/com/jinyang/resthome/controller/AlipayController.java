package com.jinyang.resthome.controller;

import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jinyang.resthome.mapper.OrdersMapper;
import com.jinyang.resthome.pojo.Orders;
import com.jinyang.resthome.pojo.TransactionRecord;
import com.jinyang.resthome.service.OrderService;
import com.jinyang.resthome.service.TransactionRecordService;
import com.jinyang.resthome.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.jinyang.resthome.config.AlipayConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.alipay.api.AlipayClient;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Description: 支付宝支付控制类
 * @ClassName: AlipayController
 * @Author: jinyang
 * @Date: 2025/3/1 15:05
 * @Version: 1.0
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private OrderService orderService; // 注入订单服务
    @Autowired
    private UserService userService;
    @Autowired
    private OrdersMapper ordersMapper; // 注入订单Mapper
    @Autowired
    private TransactionRecordService transactionRecordService;

    @GetMapping("/create-payment")
    public String createPayment(
            @RequestParam String orderId,
            @RequestParam Double amount,
            @RequestParam String subject,
            @RequestParam Long uid) throws AlipayApiException {

        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        // 将 uid 添加到回调地址
        String returnUrl = alipayConfig.getReturnUrl() + "?uid=" + uid + "&subject=" + subject;
        request.setReturnUrl(returnUrl); // 同步返回地址

        request.setNotifyUrl(alipayConfig.getNotifyUrl()); // 异步通知地址

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);      // 订单号
        bizContent.put("total_amount", amount);       // 金额
        bizContent.put("subject", subject);           // 订单标题
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY"); // 产品码

        request.setBizContent(bizContent.toJSONString());

        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
        return response.getBody(); // 返回支付宝的支付表单
    }

    @GetMapping("/return")
    public RedirectView handleReturn(HttpServletRequest request) throws AlipayApiException {
        // 1. 获取支付宝返回的订单号、subject 和 uid
        String orderNumber = request.getParameter("out_trade_no");
        String subject = request.getParameter("subject");
        String uidParam = request.getParameter("uid"); // 获取 uid 参数（可能为 null）
        System.out.println("uidParam" + uidParam);
        // 2. 校验必要参数
        if (orderNumber == null || orderNumber.isEmpty()) {
            return new RedirectView("http://localhost:5173/#/payment-failed?error=invalid_order");
        }

        // 3. 解析 uid（需处理空值）
        Long uid = null;
        try {
            if (uidParam != null && !uidParam.isEmpty()) {
                System.out.println("try");
                uid = Long.parseLong(uidParam);
            }
        } catch (NumberFormatException e) {
            return new RedirectView("http://localhost:5173/#/payment-failed?error=invalid_uid");
        }

        // 4. 根据订单号前缀路由处理逻辑
        try {
            if (orderNumber.startsWith("PAY_")) {
                return handlePaymentReturn(orderNumber, subject, uid);
            } else if (orderNumber.startsWith("RECHARGE_")) {
                return handleRechargeReturn(orderNumber, subject, uid);
            } else {
                return new RedirectView("http://localhost:5173/#/payment-failed?error=unknown_order_type");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("http://localhost:5173/#/payment-failed?error=server_error");
        }
    }

    /**
     * 处理支付订单回调
     */
    private RedirectView handlePaymentReturn(String orderNumber, String subject, Long uid) throws AlipayApiException, UnsupportedEncodingException {
        // 1. 查询支付订单
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderNumber", orderNumber);
        Orders order = ordersMapper.selectOne(queryWrapper);

        if (order == null) {
            return new RedirectView("http://localhost:5173/#/payment-failed?orderNumber=" + orderNumber + "&error=order_not_found");
        }

        // 2. 检查订单状态是否已处理（防止重复回调）
        if ("finished".equals(order.getOrderStatus())) {
            return new RedirectView("http://localhost:5173/#/payment-success?orderNumber=" + orderNumber + "&warning=already_processed");
        }

        // 3. 调用支付宝接口查询交易状态
        AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
        JSONObject queryContent = new JSONObject();
        queryContent.put("out_trade_no", orderNumber);
        queryRequest.setBizContent(queryContent.toJSONString());

        AlipayTradeQueryResponse queryResponse = alipayClient.execute(queryRequest);
        String amount = queryResponse.getTotalAmount();

        // 4. 处理交易结果
        if (queryResponse.isSuccess() && "TRADE_SUCCESS".equals(queryResponse.getTradeStatus())) {
            // 更新订单状态
            order.setOrderStatus("finished");
            ordersMapper.updateById(order);
            //添加交易记录
            TransactionRecord transactionRecord = new TransactionRecord();
            transactionRecord.setUid(uid);
            transactionRecord.setTransactionTime(new Date().toString());
            transactionRecord.setTransactionType("支付宝");
            transactionRecord.setTransactionMoney(amount);
            transactionRecord.setInOrOut(0);
            transactionRecord.setDescription(subject);
            transactionRecordService.insertTransactionRecord(transactionRecord);
            // 重定向到成功页面，携带 orderNumber、amount 和 subject
            return new RedirectView(
                    "http://localhost:5173/#/payment-success" +
                            "?orderNumber=" + orderNumber +
                            "&amount=" + amount +
                            "&subject=" + java.net.URLEncoder.encode(subject, "UTF-8") // 编码特殊字符
            );
        } else {
            order.setOrderStatus("failed");
            ordersMapper.updateById(order);
            return new RedirectView("http://localhost:5173/#/payment-failed?orderNumber=" + orderNumber);
        }
    }

    /**
     * 处理充值订单回调
     */
    private RedirectView handleRechargeReturn(String orderNumber, String subject, Long uid) throws AlipayApiException, UnsupportedEncodingException {
        // 1. 查询支付宝交易状态
        AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
        JSONObject queryContent = new JSONObject();
        queryContent.put("out_trade_no", orderNumber);
        queryRequest.setBizContent(queryContent.toJSONString());

        AlipayTradeQueryResponse queryResponse = alipayClient.execute(queryRequest);
        String amount = queryResponse.getTotalAmount();
        userService.rechargeBalance(uid, Double.parseDouble(amount));
        // 2. 检查交易是否成功
        if (queryResponse.isSuccess() && "TRADE_SUCCESS".equals(queryResponse.getTradeStatus())) {
            // 重定向到成功页面，携带 orderNumber、amount 和 subject
            return new RedirectView(
                    "http://localhost:5173/#/payment-success" +
                            "?orderNumber=" + orderNumber +
                            "&amount=" + amount +
                            "&subject=" + java.net.URLEncoder.encode(subject, "UTF-8")
            );
        } else {
            return new RedirectView("http://localhost:5173/#/payment-failed?orderNumber=" + orderNumber);
        }
    }
}