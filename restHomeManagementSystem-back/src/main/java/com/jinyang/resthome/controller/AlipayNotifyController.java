package com.jinyang.resthome.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.jinyang.resthome.config.AlipayConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 处理异步通知
 * @ClassName: AlipayNotifyController
 * @Author: jinyang
 * @Date: 2025/3/1 15:07
 * @Version: 1.0
 */
@RestController
@RequestMapping("/alipay")
public class AlipayNotifyController {

    @Autowired
    private AlipayConfig alipayConfig;

    @PostMapping("/gateway")
    public String handleGateway(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }

        // 验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayConfig.getAlipayPublicKey(),
                "UTF-8",
                "RSA2"
        );

        if (signVerified && "TRADE_SUCCESS".equals(params.get("trade_status"))) {
            String orderId = params.get("out_trade_no");
            // 更新订单状态为已支付
            return "success";
        }
        return "failure";
    }
}