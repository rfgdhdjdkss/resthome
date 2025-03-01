//package com.jinyang.resthome.interceptor;
//
//import com.jinyang.resthome.util.JwtHelper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.io.PrintWriter;
//
///**
// * @Description:
// * @ClassName: JwtInterceptor
// * @Author: jinyang
// * @Date: 2024/12/3 21:24
// * @Version: 1.0
// */
//public class JwtInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 获取请求路径
//        String requestURI = request.getRequestURI();
//        // 判断是否是登录、注册或者头像上传请求，若是则直接放行
//        if ("/user/login".equals(requestURI) || "/user/register".equals(requestURI)
//                || requestURI.startsWith("/files/") || requestURI.startsWith("/images/")) {
//            return true;
//        }
//
//        // 从请求头中获取token
//        String token = request.getHeader("Authorization");
//        if (token == null || token.isEmpty()) {
//            // 如果没有token，返回未授权状态码并拦截请求
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json;charset=utf-8");
//            PrintWriter writer = response.getWriter();
//            writer.write("{\"message\":\"未提供认证token，请先登录\"}");
//            writer.flush();
//            writer.close();
//            return false;
//        }
//
//        // 调用JwtHelper的方法验证token是否过期
//        if (JwtHelper.isTokenExpired(token)) {
//            // 如果token过期，返回未授权状态码并拦截请求
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json;charset=utf-8");
//            PrintWriter writer = response.getWriter();
//            writer.write("{\"message\":\"认证token已过期，请重新登录\"}");
//            writer.flush();
//            writer.close();
//            return false;
//        }
//
//        // 如果token有效，放行请求
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        // 可在这里添加请求处理后的逻辑，比如添加一些响应头信息等，此处暂不做具体实现
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        // 可在这里进行资源清理等操作，比如关闭数据库连接等（如果有相关操作），此处暂不做具体实现
//    }
//}