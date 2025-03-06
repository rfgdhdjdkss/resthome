package com.jinyang.resthome.controller;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.jinyang.resthome.config.AlipayConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.alipay.api.AlipayClient;

/**
 * @Description: 支付宝支付控制类
 * @ClassName: AlipayController
 * @Author: jinyang
 * @Date: 2025/3/1 15:05
 * @Version: 1.0
 */
import com.alipay.api.AlipayApiException;

import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayConfig alipayConfig;
    @GetMapping("/create-payment")
    public String createPayment(
            @RequestParam String orderId,
            @RequestParam Double amount,
            @RequestParam String subject) throws AlipayApiException {

        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(alipayConfig.getReturnUrl()); // 同步返回地址
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
        // 1. 获取支付宝同步返回的订单号
        String orderId = request.getParameter("out_trade_no");
        System.out.println("订单号: " + orderId);

        // 2. 调用支付宝查询接口验证交易状态
        AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
        JSONObject queryContent = new JSONObject();
        queryContent.put("out_trade_no", orderId);
        queryRequest.setBizContent(queryContent.toJSONString());

        AlipayTradeQueryResponse queryResponse = alipayClient.execute(queryRequest);

        // 3. 检查交易是否成功
        if (queryResponse.isSuccess() && "TRADE_SUCCESS".equals(queryResponse.getTradeStatus())) {
            // 从查询响应中获取支付金额
            String amount = queryResponse.getTotalAmount(); // 支付宝返回的金额字段
            // 重定向到前端成功页面，携带订单号和金额
            return new RedirectView("http://localhost:5173/#/payment-success?orderId=" + orderId + "&amount=" + amount);
        } else {
            // 失败时重定向到失败页面（可选携带金额）
            return new RedirectView("http://localhost:5173/#/payment-failed?orderId=" + orderId);
        }
    }


}