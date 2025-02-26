package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.mapper.GoodsMapper;
import com.jinyang.resthome.pojo.Cart;
import com.jinyang.resthome.pojo.Goods;
import com.jinyang.resthome.pojo.dto.AddNewCartRequest;
import com.jinyang.resthome.pojo.vo.userCartInfoVo;
import com.jinyang.resthome.service.CartService;
import com.jinyang.resthome.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jinyang
 * @description 针对表【cart】的数据库操作Service实现
 * @createDate 2025-02-23 21:22:44
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart>
        implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Result insertCart(Cart cart) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gid", cart.getGid());
        queryWrapper.eq("uid", cart.getUid());
        Long count = cartMapper.selectCount(queryWrapper);
        System.out.println(count);
        if (count > 0) {
            Cart cartedGoods = cartMapper.selectOne(queryWrapper);
            System.out.println(cartedGoods.toString());
            cartedGoods.setQuantity(cartedGoods.getQuantity() + 1);
            int result = cartMapper.updateById(cartedGoods);
            if (result == 1) {
                return Result.ok(cartedGoods);
            } else {
                return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
            }
        } else {
            int result = cartMapper.insert(cart);
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
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        int quantitySum = 0;
        for (Cart cart : cartList) {
            quantitySum += cart.getQuantity();
        }
        return Result.ok(quantitySum);
    }

    @Override
    public Result selectCartInfoByUid(Long uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        if (cartList != null && cartList.size() > 0) {
            List<Long> gidList = cartList.stream().map(Cart::getGid)
                    .toList();
            List<Goods> goodList = goodsMapper.selectBatchIds(gidList);
            Map<Long, Goods> goodsMap = goodList.stream().collect(Collectors.toMap(Goods::getGid, goods -> goods));
            List<userCartInfoVo> result = new ArrayList<>();
            for (Cart cart : cartList) {
                Long gid = cart.getGid();
                String image = goodsMap.get(gid).getImage();
                String title = goodsMap.get(gid).getTitle();
                Double price = goodsMap.get(gid).getPrice();
                Integer quantity = cart.getQuantity();
                result.add(new userCartInfoVo(uid, gid, image, title, price, quantity));
            }
            return Result.ok(result);
        } else {
            return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
        }
    }

    @Override
    public Result addGoodsQuantity(Integer gid, Integer uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gid", gid);
        queryWrapper.eq("uid", uid);
        Cart cart = cartMapper.selectOne(queryWrapper);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("gid", gid);
        updateWrapper.eq("uid", uid);
        updateWrapper.set("quantity", cart.getQuantity() + 1);
        int update = cartMapper.update(cart, updateWrapper);
        if (update == 1) {
            return Result.ok(cart);
        } else {
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public Result minusGoodsQuantity(Integer gid, Integer uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gid", gid);
        queryWrapper.eq("uid", uid);
        Cart cart = cartMapper.selectOne(queryWrapper);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("gid", gid);
        updateWrapper.eq("uid", uid);
        updateWrapper.set("quantity", cart.getQuantity() - 1);
        int update = cartMapper.update(cart, updateWrapper);
        if (update == 1) {
            return Result.ok(cart);
        } else {
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public Result deleteGoods(List<Long> gids, Long uid) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("gid", gids).eq("uid", uid);
        List<Cart> carts = cartMapper.selectList(queryWrapper);
        int delete = cartMapper.delete(queryWrapper);
        if (delete != 0) {
            return Result.ok(gids);
        }
        return Result.build(null, ResultCodeEnum.DELETE_ERROR);
    }
}




