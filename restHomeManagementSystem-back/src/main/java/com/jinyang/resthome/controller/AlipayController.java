package com.jinyang.resthome.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description: 支付宝支付控制类
 * @ClassName: AlipayController
 * @Author: jinyang
 * @Date: 2025/3/1 15:05
 * @Version: 1.0
 */

@RestController
public class AlipayController {

    @Autowired
    private AlipayClient alipayClient;

    @GetMapping("/create-payment")
    public String createPayment(
            @RequestParam String orderId,
            @RequestParam Double amount,
            @RequestParam String subject) throws AlipayApiException {

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl("https://f2ae-117-147-107-104.ngrok-free.app/alipay/return");
        request.setNotifyUrl("https://f2ae-117-147-107-104.ngrok-free.app/alipay/notify");

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", amount);
        bizContent.put("subject", subject);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        request.setBizContent(bizContent.toJSONString());

        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        return response.getBody(); // 返回支付宝的支付表单
    }
}