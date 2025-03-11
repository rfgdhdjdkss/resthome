package com.jinyang.resthome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jinyang
* @description 针对表【goods】的数据库操作Service
* @createDate 2025-02-23 15:05:01
*/
public interface GoodsService extends IService<Goods> {

    Result selectGoodsList();

    Result selectGoodsDetailByGid(Long gid);

    Page<Goods> findAllGoods(Page<Goods> page);

    Result deleteGoodsByGid(Long gid);

    Page<Goods> selectBySearchValue(Page<Goods> page, String searchValue);

    Result updateGoodsByGid(Goods goods);

    Result insertGoods(Goods goods);

    void updateGoodsImageByGid(String newFileName, Long gid);
}
