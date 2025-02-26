package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Cart;
import com.jinyang.resthome.pojo.dto.DeleteCartGoodsRequest;
import com.jinyang.resthome.pojo.dto.UpdateCartQuantityRequest;
import com.jinyang.resthome.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 购物车请求转发类
 * @ClassName: CartController
 * @Author: jinyang
 * @Date: 2025/2/23 21:22
 * @Version: 1.0
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 加入购物车，购物车无：新增；购物车有：数量+1
     *
     * @param cart
     * @return
     */
    @PostMapping("/addCart")
    public Result addCart(@RequestBody Cart cart) {
        Result result = cartService.insertCart(cart);
        return result;
    }

    /**
     * 计算购物车中商品数量
     *
     * @param uid
     * @return
     */
    @GetMapping("/getCartQuantity/{uid}")
    public Result getCartQuantity(@PathVariable Long uid) {
        Result result = cartService.selectCartQuantity(uid);
        return result;
    }

    /**
     * 获取uid用户的购物车内所有商品信息
     *
     * @param uid
     * @return
     */
    @GetMapping("/getCartInfoByUid/{uid}")
    public Result getCartInfoByUid(@PathVariable Long uid) {
        Result result = cartService.selectCartInfoByUid(uid);
        return result;
    }

    /**
     * 商品数量+1
     * @param request
     * @return
     */
    @PutMapping("/addGoodsQuantity")
    public Result addGoodsQuantity(@RequestBody UpdateCartQuantityRequest request) {
        Result result= cartService.addGoodsQuantity(request.getGid(),request.getUid());
        return null;
    }
    /**
     * 商品数量-1
     * @param request
     * @return
     */
    @PutMapping("/minusGoodsQuantity")
    public Result minusGoodsQuantity(@RequestBody UpdateCartQuantityRequest request) {
        Result result= cartService.minusGoodsQuantity(request.getGid(),request.getUid());
        return null;
    }
    @DeleteMapping("/deleteGoods")
    public Result deleteGoods(@RequestBody DeleteCartGoodsRequest request){
        System.out.println(request.toString());
        Result result= cartService.deleteGoods(request.getGids(),request.getUid());
        return result;

    }

}
