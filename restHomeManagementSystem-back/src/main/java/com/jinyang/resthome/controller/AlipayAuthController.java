package com.jinyang.resthome.controller;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 授权回调地址
 * @ClassName: AlipayAuthController
 * @Author: jinyang
 * @Date: 2025/3/1 16:17
 * @Version: 1.0
 */


@RestController
@RequestMapping("/alipay/auth")
public class AlipayAuthController {

    @Autowired
    private AlipayClient alipayClient;

    @GetMapping("/callback")
    public String handleCallback(@RequestParam String auth_code) throws AlipayApiException {
        // 使用 auth_code 获取 access_token
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(auth_code);
        request.setGrantType("authorization_code");

        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            String accessToken = response.getAccessToken();
            // 使用 accessToken 获取用户信息
            return "授权成功，access_token: " + accessToken;
        } else {
            return "授权失败: " + response.getSubMsg();
        }
    }
}