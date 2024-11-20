package com.jinyang.resthome.mapper;

import com.jinyang.resthome.pojo.Beds;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author jinyang
* @description 针对表【beds】的数据库操作Mapper
* @createDate 2024-11-20 19:10:29
* @Entity com.jinyang.resthome.pojo.Beds
*/
public interface BedsMapper extends BaseMapper<Beds> {

    Integer selectSpaceBedNumber();
}




