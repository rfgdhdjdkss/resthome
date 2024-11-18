package com.jinyang.resthome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.User;
import com.jinyang.resthome.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 针对我的申请业务的controller
 * @ClassName: ApplicationController
 * @Author: jinyang
 * @Date: 2024/11/14 19:10
 * @Version: 1.0
 */
@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/selectAllApplicationByUid/{uid}")
    public Result selectAllApplicationByUid(@PathVariable("uid") Long uid) {
        List applicationList = applicationService.selectAllApplicationByUid(uid);
        return Result.ok(applicationList);
    }
}
