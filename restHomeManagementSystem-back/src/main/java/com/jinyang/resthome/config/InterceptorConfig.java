package com.jinyang.resthome.config;

import com.jinyang.resthome.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Description:
 * @ClassName: InterceptorConfig
 * @Author: jinyang
 * @Date: 2024/12/3 21:24
 * @Version: 1.0
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/images/*" // 假设头像相关图片处理路径是 /images/upload/ 开头，可细化，
                );
        super.addInterceptors(registry);
    }
}
