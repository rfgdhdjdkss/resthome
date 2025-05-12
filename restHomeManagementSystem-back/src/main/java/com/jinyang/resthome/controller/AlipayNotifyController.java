package com.jinyang.resthome.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.jinyang.resthome.config.AlipayConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 处理异步通知
 * @ClassName: AlipayNotifyController
 * @Author: jinyang
 * @Date: 2025/3/1 15:07
 * @Version: 1.0
 */

import com.alipay.api.internal.util.AlipaySignature;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AlipayNotifyController {

    @Autowired
    private AlipayConfig alipayConfig;

    @PostMapping("/notify")
    public String handleNotify(HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayConfig.getAlipayPublicKey(),
                "UTF-8",
                "RSA2"
        );

        if (signVerified && "TRADE_SUCCESS".equals(params.get("trade_status"))) {
            String orderId = params.get("out_trade_no");
            System.out.println("订单 " + orderId + " 支付成功");
            return "success";
        } else {
            System.out.println("签名验证失败或交易状态不正确");
            return "failure";
        }
    }
}