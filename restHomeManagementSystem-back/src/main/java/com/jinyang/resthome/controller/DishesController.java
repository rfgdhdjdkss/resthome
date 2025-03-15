package com.jinyang.resthome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Dishes;
import com.jinyang.resthome.pojo.Dishes;
import com.jinyang.resthome.service.DishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/selectAlldishes")
    public Result selectAllDishes(
            @RequestParam(value = "currentPage") long currentPage,
            @RequestParam(value = "pageSize") long pageSize
    ) {
        Page<Dishes> page = new Page<>(currentPage, pageSize);
        Page<Dishes> dishesPage = dishesService.findAllDishes(page);
        Map<String, Object> data = new HashMap<>();
        data.put("records", dishesPage.getRecords());
        data.put("total", dishesPage.getTotal());
        data.put("pages", dishesPage.getPages());
        data.put("pageSize", dishesPage.getSize());
        data.put("currentPage", currentPage);
        data.put("hasNext", dishesPage.hasNext());
        data.put("hasPrevious", dishesPage.hasPrevious());
        return Result.ok(data);
    }


    @DeleteMapping("/deleteDishes/{gid}")
    public Result deleteDishes(@PathVariable("gid") Long gid) {
        Result result = dishesService.deleteDishesByGid(gid);
        return result;
    }

    @GetMapping("/selectForSearch")
    public Result<Object> selectForSearch(
            @RequestParam(value = "currentPage") long currentPage,
            @RequestParam(value = "pageSize") long pageSize,
            @RequestParam(value = "searchValue") String searchValue
    ) {
        Page<Dishes> page = new Page<>(currentPage, pageSize);
        Page<Dishes> userPage = dishesService.selectBySearchValue(page, searchValue);
        Map<String, Object> data = new HashMap<>();
        data.put("records", userPage.getRecords());
        data.put("total", userPage.getTotal());
        data.put("pages", userPage.getPages());
        data.put("pageSize", userPage.getSize());
        data.put("currentPage", currentPage);
        data.put("hasNext", userPage.hasNext());
        data.put("hasPrevious", userPage.hasPrevious());
        return Result.ok(data);
    }

    @PutMapping("/updateDishes")
    public Result updateDishes(@RequestBody Dishes Dishes) {
        Result result = dishesService.updateDishesByGid(Dishes);
        return Result.ok(result);
    }
    @PostMapping("/addDishes")
    public Result addDishes(@RequestBody Dishes Dishes) {
        Result result = dishesService.insertDishes(Dishes);
        return Result.ok(result);
    }
}
