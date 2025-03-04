//package com.jinyang.resthome.service;
//
//
///**
// * @Description: 支付服务类
// * @ClassName: AlipayService
// * @Author: jinyang
// * @Date: 2025/3/1 14:55
// * @Version: 1.0
// */
//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipayTradePagePayRequest;
//import com.jinyang.resthome.config.AlipayConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AlipayService {
//
//    @Autowired
//    private AlipayConfig alipayConfig;
//
//    public String createOrder(String outTradeNo, String totalAmount, String subject) throws AlipayApiException {
//        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(), "json", "UTF-8", alipayConfig.getAlipayPublicKey(), "RSA2");
//        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
//        request.setReturnUrl(alipayConfig.getReturnUrl());
//        request.setNotifyUrl(alipayConfig.getNotifyUrl());
//
//        request.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
//                + "\"total_amount\":\"" + totalAmount + "\","
//                + "\"subject\":\"" + subject + "\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//        System.out.println(request);
//        System.out.println(alipayClient.pageExecute(request).getBody());
//        return alipayClient.pageExecute(request).getBody();
//    }
//}