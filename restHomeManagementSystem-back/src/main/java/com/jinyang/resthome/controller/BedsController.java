package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.service.BedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @ClassName: BedsController
 * @Author: jinyang
 * @Date: 2024/11/20 20:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("/beds")
public class BedsController {

    @Autowired
    private BedsService bedsService;
    @GetMapping("/getSpaceBedNumber")
    public Result<Integer> getSpaceBedNumber() {
        Integer result= bedsService.getSpaceBedNumber();
        return Result.ok(result);
    }

}
