package com.jinyang.resthome.service;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Dishcart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinyang.resthome.pojo.Dishes;

import java.util.List;

/**
* @author jinyang
* @description 针对表【dishCart】的数据库操作Service
* @createDate 2025-03-15 16:56:38
*/
public interface DishcartService extends IService<Dishcart> {

    Result insertDishes(Dishcart dishcart);

    Result selectCartQuantity(Long uid);

    Result selectCartInfoByUid(Long uid);



    Result updateQuantity(Integer dishId, Integer uid, Integer quantity);

    Result deleteByDishIdWithUid(Long dishId, Long uid);

    Result clearByUid(Long uid);

    Result selectDishInfoByDishIdList(List<Long> dishIdList, Long uid);
}
