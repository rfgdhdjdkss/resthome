package com.jinyang.resthome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Dishes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jinyang
* @description 针对表【dishes】的数据库操作Service
* @createDate 2024-12-15 15:15:35
*/
public interface DishesService extends IService<Dishes> {

    Dishes addDish(Dishes dishes);

    List<Dishes> getAllDishes();

    void updateDishImgByDishId(String newFileName, Long dishId);

    Page<Dishes> findAllDishes(Page<Dishes> page);

    Result deleteDishesByGid(Long gid);

    Page<Dishes> selectBySearchValue(Page<Dishes> page, String searchValue);

    Result updateDishesByGid(Dishes dishes);

    Result insertDishes(Dishes dishes);
}
