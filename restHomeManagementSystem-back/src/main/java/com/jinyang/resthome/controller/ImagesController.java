package com.jinyang.resthome.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description:
 * @ClassName: ImagesController
 * @Author: jinyang
 * @Date: 2024/12/11 20:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/images")
public class ImagesController {

    @GetMapping("/upload/headPortrait/{headImgUrl}")
    public void getImages(@PathVariable String headImgUrl, HttpServletResponse response) {
        // 构建图片文件在静态资源目录下的完整路径
        String filePath = "static/images/upload/headPortrait/" + headImgUrl;
        System.out.println(filePath);
        try {
            // 使用Spring的ClassPathResource来加载资源
            Resource resource = new ClassPathResource(filePath);
            if (resource.exists()) {
                // 获取文件的输入流
                InputStream inputStream = resource.getInputStream();
                // 设置响应头信息，指定返回的内容类型为图片类型（根据实际图片格式调整）
                String contentType = getContentType(headImgUrl);
                response.setContentType(contentType);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + headImgUrl);
                // 获取输出流，将图片数据写入响应输出流，返回给客户端
                ServletOutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();
            } else {
                // 如果文件不存在，设置相应的错误状态码和提示信息（这里简单设置404）
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("图片资源不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 如果出现IO异常，设置服务器内部错误状态码（这里简单设置500）
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("服务器内部错误");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getContentType(String fileName) {
        // 根据文件名后缀判断图片类型，设置对应的Content-Type，这里简单示例几种常见图片格式
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if (fileName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF_VALUE;
        }
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }



    @GetMapping("/upload/addNewDish/dishImg/{dishImg}")
    public void getDishImages(@PathVariable String dishImg, HttpServletResponse response) {
        // 构建图片文件在静态资源目录下的完整路径
        String filePath = "static/images/upload/dishImg/" + dishImg;
        System.out.println(filePath);
        try {
            // 使用Spring的ClassPathResource来加载资源
            Resource resource = new ClassPathResource(filePath);
            if (resource.exists()) {
                // 获取文件的输入流
                InputStream inputStream = resource.getInputStream();
                // 设置响应头信息，指定返回的内容类型为图片类型（根据实际图片格式调整）
                String contentType = getContentType(dishImg);
                response.setContentType(contentType);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dishImg);
                // 获取输出流，将图片数据写入响应输出流，返回给客户端
                ServletOutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();
            } else {
                // 如果文件不存在，设置相应的错误状态码和提示信息（这里简单设置404）
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("图片资源不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 如果出现IO异常，设置服务器内部错误状态码（这里简单设置500）
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("服务器内部错误");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



    @GetMapping("/upload/goodsImg/{goodsImg}")
    public void getGoodsImages(@PathVariable String goodsImg, HttpServletResponse response) {
        // 构建图片文件在静态资源目录下的完整路径
        String filePath = "static/images/upload/goodsImg/" + goodsImg;
        System.out.println(filePath);
        try {
            // 使用Spring的ClassPathResource来加载资源
            Resource resource = new ClassPathResource(filePath);
            if (resource.exists()) {
                // 获取文件的输入流
                InputStream inputStream = resource.getInputStream();
                // 设置响应头信息，指定返回的内容类型为图片类型（根据实际图片格式调整）
                String contentType = getContentType(goodsImg);
                response.setContentType(contentType);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + goodsImg);
                // 获取输出流，将图片数据写入响应输出流，返回给客户端
                ServletOutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();
            } else {
                // 如果文件不存在，设置相应的错误状态码和提示信息（这里简单设置404）
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("图片资源不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 如果出现IO异常，设置服务器内部错误状态码（这里简单设置500）
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("服务器内部错误");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}