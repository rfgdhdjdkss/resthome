package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @ClassName: RoomsController
 * @Author: jinyang
 * @Date: 2024/11/20 20:42
 * @Version: 1.0
 */
@RestController
@RequestMapping("/rooms")
public class RoomsController {
    @Autowired
    private RoomsService roomsService;

    @GetMapping("/getSpaceRoomNumber")
    public Result getSpaceRoomNumber() {
        Integer result= roomsService.getSpaceRoomNumber();
        return Result.ok(result);
    }
}
