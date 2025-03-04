package com.jinyang.resthome.config;


/**
 * @Description: 支付宝接口配置类
 * @ClassName: AlipayConfig
 * @Author: jinyang
 * @Date: 2025/2/28 19:29
 * @Version: 1.0
 */
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
public class AlipayConfig {

    @Value("${alipay.sandbox.app-id}")
    private String appId;

    @Value("${alipay.sandbox.private-key}")
    private String privateKey;

    @Value("${alipay.sandbox.alipay-public-key}")
    private String alipayPublicKey;

    @Value("${alipay.sandbox.notify-url}")
    private String notifyUrl;

    @Value("${alipay.sandbox.return-url}")
    private String returnUrl;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(
                "https://openapi-sandbox.dl.alipaydev.com/gateway.do", // 沙箱网关地址
                appId,
                privateKey,
                "json",
                "UTF-8",
                alipayPublicKey,
                "RSA2"
        );
    }
}