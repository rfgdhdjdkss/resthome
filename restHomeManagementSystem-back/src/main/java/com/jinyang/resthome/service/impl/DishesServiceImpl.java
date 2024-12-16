package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.pojo.Dishes;
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
    public Dishes addDish(Dishes dishes) {
        dishesMapper.insert(dishes);
        QueryWrapper<Dishes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dishName", dishes.getDishName()).eq("dishQuantity", dishes.getDishQuantity()).eq("dishPrice", dishes.getDishPrice());
        List<Dishes> newDish = dishesMapper.selectList(queryWrapper);
        System.out.println(newDish);
        return newDish.get(0);
    }


}




