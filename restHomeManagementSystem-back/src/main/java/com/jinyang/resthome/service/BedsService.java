package com.jinyang.resthome.service;

import com.jinyang.resthome.pojo.Beds;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinyang
* @description 针对表【beds】的数据库操作Service
* @createDate 2024-11-20 19:10:29
*/
public interface BedsService extends IService<Beds> {

    Integer getSpaceBedNumber();
}
