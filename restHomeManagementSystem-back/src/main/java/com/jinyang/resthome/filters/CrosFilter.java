package com.jinyang.resthome.filters;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @Description: 跨域过滤器
 * @ClassName: CrosFilter
 * @Author: jinyang
 * @Date: 2024/10/3 16:47
 * @Version: 1.0
 */

// 拦截所有请求
@WebFilter(urlPatterns = "/*")
public class CrosFilter implements Filter {

    // 初始化方法，一般不用
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 跨域解决
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");

        // 放行函数
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 销毁函数
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
