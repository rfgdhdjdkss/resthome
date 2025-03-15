package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.pojo.Dishes;
import com.jinyang.resthome.pojo.Goods;
import com.jinyang.resthome.pojo.User;
import com.jinyang.resthome.service.DishesService;
import com.jinyang.resthome.mapper.DishesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【dishes】的数据库操作Service实现
 * @createDate 2024-12-15 15:15:35
 */
@Service
public class DishesServiceImpl extends ServiceImpl<DishesMapper, Dishes>
        implements DishesService {
    @Autowired
    private DishesMapper dishesMapper;

    @Override
    public Dishes addDish(Dishes dishes) {
        return null;
    }

    @Override
    public List<Dishes> getAllDishes() {
        return dishesMapper.selectList(null);
    }

    @Override
    public void updateDishImgByDishId(String newFileName, Long dishId) {
        UpdateWrapper<Dishes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("dishId", dishId);
        updateWrapper.set("dishImg", newFileName);
        dishesMapper.update(updateWrapper);
    }

    @Override
    public Page<Dishes> findAllDishes(Page<Dishes> page) {
        Page<Dishes> Dishes = dishesMapper.selectPage(page, null);
        return Dishes;
    }

    @Override
    public Result deleteDishesByGid(Long gid) {
        int result = dishesMapper.deleteById(gid);
        if (result != 1) {
            return Result.build(null, ResultCodeEnum.DELETE_ERROR);
        } else {
            return Result.ok(null);
        }
    }

    @Override
    public Page<Dishes> selectBySearchValue(Page<Dishes> page, String searchValue) {
        LambdaQueryWrapper<Dishes> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Dishes::getDishName, searchValue).or().like(Dishes::getDescription, searchValue);
        Page<Dishes> dishes = dishesMapper.selectPage(page, wrapper);
        return dishes;
    }

    @Override
    public Result updateDishesByGid(Dishes dishes) {
        UpdateWrapper<Dishes> wrapper = new UpdateWrapper<>();
        wrapper.eq("dishId", dishes.getDishId());
        int update = dishesMapper.update(dishes, wrapper);
        if (update != 1) {
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }
        return Result.ok(dishes);
    }

    @Override
    public Result insertDishes(Dishes dishes) {
        int insert = dishesMapper.insert(dishes);
        if (insert != 1) {
            Result.build(null,ResultCodeEnum.INSERT_FAIL);
        }
        return Result.ok(insert);
    }
}








