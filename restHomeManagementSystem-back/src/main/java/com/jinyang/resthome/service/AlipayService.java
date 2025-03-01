package com.jinyang.resthome.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.jinyang.resthome.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @Description: 支付服务类
 * @ClassName: AlipayService
 * @Author: jinyang
 * @Date: 2025/3/1 14:55
 * @Version: 1.0
 */
@Service
public class AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayConfig alipayConfig;

    public String createPayment(String orderId, Double amount, String subject) throws Exception {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(alipayConfig.getReturnUrl());
        request.setNotifyUrl(alipayConfig.getNotifyUrl());

        // 构建支付参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);      // 订单号
        bizContent.put("total_amount", amount);       // 金额
        bizContent.put("subject", subject);           // 订单标题
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY"); // 产品码

        request.setBizContent(bizContent.toJSONString());

        // 发起支付请求
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        return response.getBody();
    }
}