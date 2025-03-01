package com.jinyang.resthome.config;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @Description: 支付宝接口配置类
 * @ClassName: AlipayConfig
 * @Author: jinyang
 * @Date: 2025/2/28 19:29
 * @Version: 1.0
 */
@Configuration
@Data
public class AlipayConfig {

    @Value("${alipay.app-id}")
    private String appId;

    @Value("${alipay.merchant-private-key}")
    private String merchantPrivateKey;

    @Value("${alipay.alipay-public-key}")
    private String alipayPublicKey;
    @Value("${alipay.notify-url}")
    private String notifyUrl;

    @Value("${alipay.return-url}")
    private String returnUrl;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do", // 沙箱网关地址
                appId,
                merchantPrivateKey,
                "json",
                "UTF-8",
                alipayPublicKey,
                "RSA2"
        );
    }
}