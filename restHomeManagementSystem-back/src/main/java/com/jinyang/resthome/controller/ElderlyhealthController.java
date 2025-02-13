package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.service.ElderlyhealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @ClassName: ElderlyhealthController
 * @Author: jinyang
 * @Date: 2025/2/13 15:21
 * @Version: 1.0
 */
@RestController()
@RequestMapping("/eHealth")
public class ElderlyhealthController {
    @Autowired
    private ElderlyhealthService elderlyhealthService;
    @GetMapping("/getHealthList/{uid}")
    public Result getHealthListByUid(@PathVariable("uid") Long uid) {
        Result result= elderlyhealthService.selectHealthListByUid(uid);
        return result;
    }
    @GetMapping("/getHealthDetail/{eid}")
    public Result getHealthDetailByEid(@PathVariable("eid") Long eid) {
        Result result= elderlyhealthService.selectHealthDetailByEid(eid);
        return result;
    }
}
