package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Dishcart;
import com.jinyang.resthome.pojo.dto.DeleteDishCartRequest;
import com.jinyang.resthome.pojo.dto.GetDishesInfoByDishIdListRequest;
import com.jinyang.resthome.pojo.dto.GetGoodsInfoByGidListRequest;
import com.jinyang.resthome.pojo.dto.UpdateDishCartQuantityRequest;
import com.jinyang.resthome.service.DishcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 餐品购物车
 * @ClassName: DishCartController
 * @Author: jinyang
 * @Date: 2025/3/15 16:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dCart")
public class DishCartController {
    @Autowired
    private DishcartService dishcartService;

    /**
     * 加入购物车，购物车无：新增；购物车有：数量+1
     *
     * @param dishcart
     * @return
     */
    @PostMapping("/addDishes")
    public Result addCart(@RequestBody Dishcart dishcart) {
        Result result = dishcartService.insertDishes(dishcart);
        return result;
    }

    /**
     * 计算购物车中商品数量
     *
     * @param uid
     * @return
     */
    @GetMapping("/getDishCartQuantity/{uid}")
    public Result getDishCartQuantity(@PathVariable Long uid) {
        Result result = dishcartService.selectCartQuantity(uid);
        return result;
    }


    /**
     * 获取uid用户的购物车内所有商品信息
     *
     * @param uid
     * @return
     */
    @GetMapping("/getDishCartInfoByUid/{uid}")
    public Result getDishCartInfoByUid(@PathVariable Long uid) {
        Result result = dishcartService.selectCartInfoByUid(uid);
        return result;
    }

    /**
     * 商品数量+1
     *
     * @param request
     * @return
     */
    @PutMapping("/updateQuantity")
    public Result updateQuantity(@RequestBody UpdateDishCartQuantityRequest request) {
        Result result = dishcartService.updateQuantity(request.getDishId(), request.getUid(), request.getQuantity());
        return null;
    }

    @DeleteMapping("/delete")
    public Result deleteByDishIdWithUid(@RequestBody DeleteDishCartRequest request) {
        Result result = dishcartService.deleteByDishIdWithUid(request.getDishId(), request.getUid());
        return null;
    }

    @DeleteMapping("/clearByUid/{uid}")
    public Result clearByUid(@PathVariable Long uid) {
        Result result = dishcartService.clearByUid(uid);
        return result;
    }

    /**
     * 获取餐品信息根据餐品id列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getDishInfoByDishIdList")
    public Result getDishInfoByDishIdList(@RequestBody GetDishesInfoByDishIdListRequest request) {
        System.out.println(request.toString());
        Result result = dishcartService.selectDishInfoByDishIdList(request.getDishIdList(), request.getUid());
        return result;
    }

    @PutMapping("/minusdishesQuantity")
    public Result minusdishesQuantity(@RequestBody UpdateDishCartQuantityRequest request) {
        System.out.println(request.toString());
        Result result = dishcartService.updateQuantity(request.getDishId(), request.getUid(), request.getQuantity());

        return result;
    }

    @PutMapping("/adddishesQuantity")
    public Result adddishesQuantity(@RequestBody UpdateDishCartQuantityRequest request) {
        System.out.println(request.toString());
        Result result = dishcartService.updateQuantity(request.getDishId(), request.getUid(), request.getQuantity());

        return result;
    }
}
