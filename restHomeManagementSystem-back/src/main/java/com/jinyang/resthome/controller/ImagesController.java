package com.jinyang.resthome.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: 图片资源控制器
 * @ClassName: ImagesController
 * @Author: jinyang
 * @Date: 2024/12/11 20:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/images")
public class ImagesController {

    @GetMapping("/upload/headPortrait/{headImgUrl}")
    public ResponseEntity<Resource> getHeadPortrait(@PathVariable String headImgUrl) {
        String filePath = "static/images/upload/headPortrait/" + headImgUrl;
        return buildImageResponse(filePath, headImgUrl);
    }

    @GetMapping("/upload/addNewDish/dishImg/{dishImg}")
    public ResponseEntity<Resource> getDishImages(@PathVariable String dishImg) {
        String filePath = "static/images/upload/dishImg/" + dishImg;
        return buildImageResponse(filePath, dishImg);
    }

    @GetMapping("/upload/goodsImg/{goodsImg}")
    public ResponseEntity<Resource> getGoodsImages(@PathVariable String goodsImg) {
        String filePath = "static/images/upload/goodsImg/" + goodsImg;
        return buildImageResponse(filePath, goodsImg);
    }

    @GetMapping("/upload/elderlyImages/{image}")
    public ResponseEntity<Resource> getElderlyImages(@PathVariable String image) {
        String filePath = "static/images/upload/elderlyImages/" + image;
        return buildImageResponse(filePath, image);
    }

    private ResponseEntity<Resource> buildImageResponse(String filePath, String fileName) {
        Resource resource = new ClassPathResource(filePath);
        if (resource.exists()) {
            String contentType = getContentType(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if (fileName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF_VALUE;
        }
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
}