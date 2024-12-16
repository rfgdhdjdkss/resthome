package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Dishes;
import com.jinyang.resthome.service.DishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 菜品管理Controller
 * @ClassName: DishesController
 * @Author: jinyang
 * @Date: 2024/12/15 15:16
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dishes")
public class DishesController {
    @Autowired
    private DishesService dishesService;

    @GetMapping("/findAllDishes")
    public Result findAllDishes() {
        List<Dishes> dishesList = dishesService.getAllDishes();
        return Result.ok(dishesList);
    }

    @PostMapping("/addDish")
    public Result addDish(@RequestBody Dishes dishes) {
        System.out.println(dishes.toString());
        Dishes newDish = dishesService.addDish(dishes);
        return Result.ok(newDish);
    }
}
