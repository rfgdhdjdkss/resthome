package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Bedroom;
import com.jinyang.resthome.pojo.vo.BedRoomVo;
import com.jinyang.resthome.service.BedroomService;
import com.jinyang.resthome.service.BedsService;
import com.jinyang.resthome.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: BedController
 * @Author: jinyang
 * @Date: 2024/10/27 17:06
 * @Version: 1.0
 */
@RestController
@RequestMapping("/bedroom")
public class BedroomController {
    @Autowired
    private BedroomService bedroomService;
    /**
     * 获取所有房间床位信息
     * @return
     */
    @GetMapping("/selectBedInfo")
    public Result selectBedInfo() {
        List<BedRoomVo> bedroomList= bedroomService.selectBedInfo();
        Map<String,Object> result=new HashMap<>();
        result.put("bedroomList",bedroomList);
        return Result.ok(result);
    }
}
