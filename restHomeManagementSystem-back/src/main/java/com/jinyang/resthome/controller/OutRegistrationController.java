package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Outregistration;
import com.jinyang.resthome.service.OutregistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @ClassName: OutRegistrationController
 * @Author: jinyang
 * @Date: 2024/11/13 20:26
 * @Version: 1.0
 */
@RestController
@RequestMapping("/outRegistration")
public class OutRegistrationController {
    @Autowired
    private OutregistrationService outregistrationService;

    @PostMapping("/add")
    public Result<Outregistration> add(@RequestBody Outregistration outregistration) {
        Result result = outregistrationService.saveOutregistration(outregistration);
        return result;
    }
}
