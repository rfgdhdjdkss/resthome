package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.service.UserService;
import com.jinyang.resthome.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description:
 * @ClassName: FileController
 * @Author: jinyang
 * @Date: 2024/11/5 13:22
 * @Version: 1.0
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private UserService userService;
    //文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/upload";

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/{uid}")
    public String getFileName(MultipartFile file, @PathVariable Long uid) {
        String oldFileName = file.getOriginalFilename();
        System.out.println(oldFileName);
        String filePath = FileUtil.getUpLoadFilePath();
        String newFileName = System.currentTimeMillis() + oldFileName;
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
            userService.updateHeadImgUrlByUid(newFileName, uid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFileName;
    }


}
