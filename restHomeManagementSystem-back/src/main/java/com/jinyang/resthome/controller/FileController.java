package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.service.DishesService;
import com.jinyang.resthome.service.GoodsService;
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
    @Autowired
    private DishesService dishesService;
@Autowired
private GoodsService goodsService;

    //文件上传存储路径
//    private static final String filePath = System.getProperty("user.dir") + "/upload";

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/{uid}")
    public String getFileName(MultipartFile file, @PathVariable Long uid) {
        String oldFileName = file.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath() + "/headPortrait";
        String newFileName = System.currentTimeMillis() + oldFileName;
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
            userService.updateHeadImgUrlByUid(newFileName, uid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFileName;
    }

    @PostMapping("/upload/addNewDish/{dishId}")
    public String getDishFileName(MultipartFile file, @PathVariable Long dishId) {
        String oldFileName = file.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath() + "/dishImg";
        String newFileName = System.currentTimeMillis() + oldFileName;
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
            dishesService.updateDishImgByDishId(newFileName, dishId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFileName;

    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/addNewGoods/{gid}")
    public String getGoodsImgName(MultipartFile file, @PathVariable Long gid) {
        String oldFileName = file.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath() + "/goodsImg";
        String newFileName = System.currentTimeMillis() + oldFileName;
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
            goodsService.updateGoodsImageByGid(newFileName, gid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFileName;
    }
    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/addNewGoods")
    public String uploadGoodsImgName(MultipartFile file) {
        String oldFileName = file.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath() + "/goodsImg";
        String newFileName = System.currentTimeMillis() + oldFileName;
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFileName;
    }
    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/addElderly/{eid}")
    public String getElderlyImgName(MultipartFile file, @PathVariable Long eid) {
        String oldFileName = file.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath() + "/elderlyImages";
        String newFileName = System.currentTimeMillis() + oldFileName;
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
            userService.updateHeadImgUrlByUid(newFileName, eid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFileName;
    }
}
