package com.jinyang.resthome.service;

import com.jinyang.resthome.pojo.Bedroom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinyang.resthome.pojo.vo.BedRoomVo;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【bedroom】的数据库操作Service
 * @createDate 2024-10-27 17:05:50
 */
public interface BedroomService extends IService<Bedroom> {
    //获取所有房间床位信息业务
    public List<BedRoomVo> selectBedInfo();
}
