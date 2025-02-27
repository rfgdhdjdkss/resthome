package com.jinyang.resthome.service;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinyang.resthome.pojo.dto.AddNewCartRequest;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【cart】的数据库操作Service
 * @createDate 2025-02-23 21:22:44
 */
public interface CartService extends IService<Cart> {

    Result insertCart(Cart cart);

    Result selectCartQuantity(Long uid);

    Result selectCartInfoByUid(Long uid);

    Result addGoodsQuantity(Integer gid, Integer uid);

    Result minusGoodsQuantity(Integer gid, Integer uid);

    Result deleteGoods(List<Long> gids, Long uid);

    Result selectGoodsListByGidList(List<Long> gidList, Long uid);

}
