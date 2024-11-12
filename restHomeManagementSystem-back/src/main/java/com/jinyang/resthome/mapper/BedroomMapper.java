package com.jinyang.resthome.mapper;

import com.jinyang.resthome.pojo.Bedroom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinyang.resthome.pojo.vo.BedRoomVo;

import java.util.List;

/**
* @author jinyang
* @description 针对表【bedroom】的数据库操作Mapper
* @createDate 2024-10-27 17:05:50
* @Entity com.jinyang.resthome.pojo.Bedroom
*/
public interface BedroomMapper extends BaseMapper<Bedroom> {

    List<BedRoomVo> selectBedInfo();
}




