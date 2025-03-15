package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.mapper.DishesMapper;
import com.jinyang.resthome.pojo.Cart;
import com.jinyang.resthome.pojo.Dishcart;
import com.jinyang.resthome.pojo.Dishes;
import com.jinyang.resthome.pojo.Goods;
import com.jinyang.resthome.pojo.vo.UserDishOrderInfoVo;
import com.jinyang.resthome.pojo.vo.UserOrderInfoVo;
import com.jinyang.resthome.pojo.vo.userCartInfoVo;
import com.jinyang.resthome.pojo.vo.userDishCartInfoVo;
import com.jinyang.resthome.service.DishcartService;
import com.jinyang.resthome.mapper.DishcartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jinyang
 * @description 针对表【dishCart】的数据库操作Service实现
 * @createDate 2025-03-15 16:56:38
 */
@Service
public class DishcartServiceImpl extends ServiceImpl<DishcartMapper, Dishcart>
        implements DishcartService {
    @Autowired
    private DishcartMapper dishcartMapper;
    @Autowired
    private DishesMapper dishesMapper;

    @Override
    public Result insertDishes(Dishcart dishcart) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dishId", dishcart.getDishId());
        queryWrapper.eq("uid", dishcart.getUid());
        Long count = dishcartMapper.selectCount(queryWrapper);
        System.out.println(count);
        if (count > 0) {
            Dishcart dishes = dishcartMapper.selectOne(queryWrapper);
            dishes.setQuantity(dishes.getQuantity() + 1);
            int result = dishcartMapper.updateById(dishes);
            if (result == 1) {
                return Result.ok(dishes);
            } else {
                return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
            }
        } else {
            int result = dishcartMapper.insert(dishcart);
            if (result == 1) {
                return Result.ok(result);
            } else {
                return Result.build(null, ResultCodeEnum.INSERT_FAIL);
            }
        }
    }

    @Override
    public Result selectCartQuantity(Long uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Dishcart> dishcartList = dishcartMapper.selectList(queryWrapper);
        int quantitySum = 0;
        for (Dishcart dishcart : dishcartList) {
            quantitySum += dishcart.getQuantity();
        }
        return Result.ok(quantitySum);
    }

    @Override
    public Result selectCartInfoByUid(Long uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Dishcart> dishcartList = dishcartMapper.selectList(queryWrapper);
        if (dishcartList != null && dishcartList.size() > 0) {
            List<Long> dishIdList = dishcartList.stream().map(Dishcart::getDishId)
                    .toList();
            List<Dishes> dishesList = dishesMapper.selectBatchIds(dishIdList);
            Map<Long, Dishes> DishMap = dishesList.stream().collect(Collectors.toMap(Dishes::getDishId, dishes -> dishes));
            List<userDishCartInfoVo> result = new ArrayList<>();
            for (Dishcart cart : dishcartList) {
                Long dishId = cart.getDishId();
                String image = DishMap.get(dishId).getDishImg();
                String title = DishMap.get(dishId).getDishName();
                Double price = DishMap.get(dishId).getDishPrice();
                Integer quantity = cart.getQuantity();
                result.add(new userDishCartInfoVo(uid, dishId, image, title, price, quantity));
            }
            return Result.ok(result);
        } else {
            return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
        }

    }

    @Override
    public Result updateQuantity(Integer dishId, Integer uid, Integer quantity) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dishId", dishId);
        queryWrapper.eq("uid", uid);
        Dishcart dishcart = dishcartMapper.selectOne(queryWrapper);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("dishId", dishId);
        updateWrapper.eq("uid", uid);
        updateWrapper.set("quantity", quantity);
        int update = dishcartMapper.update(dishcart, updateWrapper);
        if (update == 1) {
            return Result.ok(dishcart);
        } else {
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public Result deleteByDishIdWithUid(Long dishId, Long uid) {
        System.out.println(dishId);
        System.out.println(uid);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dishId", dishId);
        queryWrapper.eq("uid", uid);
        int delete = dishcartMapper.delete(queryWrapper);
        System.out.println(delete);
        if (delete == 1) {
            Result.ok(null);
        }
        return Result.build(null,ResultCodeEnum.DELETE_ERROR);
    }

    @Override
    public Result clearByUid(Long uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        int delete = dishcartMapper.delete(queryWrapper);
        if (delete == 1) {
            return Result.ok(null);
        }
        return Result.build(null,ResultCodeEnum.DELETE_ERROR);
    }

    @Override
    public Result selectDishInfoByDishIdList(List<Long> dishIdList, Long uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("dishId", dishIdList);
        //商品信息，获取商品名，商品价格，商品运费等信息
        List<Dishes> dishesList = dishesMapper.selectList(queryWrapper);
        System.out.println("dishesList"+dishesList);
        //获取购物车中，商品的数量
        List<Dishcart> dishcartList = dishcartMapper.selectList(queryWrapper);
        System.out.println("dishcart"+dishcartList);

        Map<Long, Dishes> dishesMap = dishesList.stream().collect(Collectors.toMap(Dishes::getDishId, dishes -> dishes));
        List<UserDishOrderInfoVo> result = new ArrayList<>();
        for (Dishcart dishcart : dishcartList) {
            Long dishId = dishcart.getDishId();
            String dishImg = dishesMap.get(dishId).getDishImg();
            String dishName = dishesMap.get(dishId).getDishName();
            Double dishPrice = dishesMap.get(dishId).getDishPrice();
            Integer quantity = dishcart.getQuantity();
            result.add(new UserDishOrderInfoVo(dishId, uid, dishName, dishImg, dishPrice, quantity));
        }
        return Result.ok(result);
    }

}




