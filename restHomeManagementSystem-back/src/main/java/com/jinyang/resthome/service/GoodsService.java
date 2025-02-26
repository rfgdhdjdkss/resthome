package com.jinyang.resthome.service;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinyang
* @description 针对表【goods】的数据库操作Service
* @createDate 2025-02-23 15:05:01
*/
public interface GoodsService extends IService<Goods> {

    Result selectGoodsList();

    Result selectGoodsDetailByGid(Long gid);
}
