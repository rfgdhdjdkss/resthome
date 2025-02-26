package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.pojo.Goods;
import com.jinyang.resthome.service.GoodsService;
import com.jinyang.resthome.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【goods】的数据库操作Service实现
 * @createDate 2025-02-23 15:05:01
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
        implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Result selectGoodsList() {
        List<Goods> goods = goodsMapper.selectList(null);
        if (goods != null && !goods.isEmpty()) {
            return Result.ok(goods);
        } else {
            return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
        }
    }

    @Override
    public Result selectGoodsDetailByGid(Long gid) {
        Goods goods = goodsMapper.selectById(gid);
        if (goods != null) {
            return Result.ok(goods);
        }else {
            return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
        }
    }
}




