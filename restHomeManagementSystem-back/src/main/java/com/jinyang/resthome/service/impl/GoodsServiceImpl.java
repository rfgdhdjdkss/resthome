package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.pojo.Goods;
import com.jinyang.resthome.pojo.User;
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
        } else {
            return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
        }
    }

    @Override
    public Page<Goods> findAllGoods(Page<Goods> page) {
        //条件构造器，权限不等于管理员
        Page<Goods> goods = goodsMapper.selectPage(page, null);
        return goods;
    }

    @Override
    public Result deleteGoodsByGid(Long gid) {
        int result = goodsMapper.deleteById(gid);
        if (result != 1) {
            return Result.build(null, ResultCodeEnum.DELETE_ERROR);
        } else {
            return Result.ok(null);
        }
    }

    @Override
    public Page<Goods> selectBySearchValue(Page<Goods> page, String searchValue) {

            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(Goods::getTitle, searchValue).or().like(Goods::getDescription, searchValue);
            Page<Goods> goods = goodsMapper.selectPage(page, wrapper);
            return goods;
        }

    @Override
    public Result updateGoodsByGid(Goods goods) {
            UpdateWrapper<Goods> wrapper = new UpdateWrapper<>();
            wrapper.eq("gid", goods.getGid());
            int update = goodsMapper.update(goods, wrapper);
            if (update != 1) {
                return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
            }
            return Result.ok(goods);
        }

    @Override
    public Result insertGoods(Goods goods) {
        int insert = goodsMapper.insert(goods);
        if (insert != 1) {
            Result.build(null,ResultCodeEnum.INSERT_FAIL);
        }
        return Result.ok(insert);
    }

    @Override
    public void updateGoodsImageByGid(String newFileName, Long gid) {
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gid", gid);
        updateWrapper.set("image", newFileName);
        goodsMapper.update(updateWrapper);
    }


}




