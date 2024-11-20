package com.jinyang.resthome.service;

import com.jinyang.resthome.pojo.Rooms;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinyang
* @description 针对表【rooms】的数据库操作Service
* @createDate 2024-11-20 20:27:58
*/
public interface RoomsService extends IService<Rooms> {

    Integer getSpaceRoomNumber();
}
