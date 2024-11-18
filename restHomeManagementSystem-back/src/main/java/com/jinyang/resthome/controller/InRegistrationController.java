package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Inregistration;
import com.jinyang.resthome.pojo.Outregistration;
import com.jinyang.resthome.service.InregistrationService;
import com.jinyang.resthome.service.OutregistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @ClassName: InRegistrationController
 * @Author: jinyang
 * @Date: 2024/11/13 20:42
 * @Version: 1.0
 */
@RestController
@RequestMapping("/inRegistration")
public class InRegistrationController {
    @Autowired
    private InregistrationService inregistrationService;

    @PostMapping("/add")
    public Result<Inregistration> add(@RequestBody Inregistration inregistration) {
        Result result = inregistrationService.saveInregistration(inregistration);
        return result;
    }
}
