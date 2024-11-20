package com.jinyang.resthome.mapper;

import com.jinyang.resthome.pojo.Rooms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinyang.resthome.pojo.vo.BedRoomVo;

/**
* @author jinyang
* @description 针对表【rooms】的数据库操作Mapper
* @createDate 2024-11-20 20:27:58
* @Entity com.jinyang.resthome.pojo.Rooms
*/
public interface RoomsMapper extends BaseMapper<Rooms> {

    BedRoomVo selectForBedRoom(String roomType);

    Integer selectSpaceRoomNumber();
}




