package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.dto.GetGoodsInfoByGidListRequest;
import com.jinyang.resthome.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 商品控制类
 * @ClassName: GoodsController
 * @Author: jinyang
 * @Date: 2025/2/23 15:05
 * @Version: 1.0
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 获取全部商品信息
     *
     * @return
     */
    @GetMapping("/getGoodsList")
    public Result getGoodsList() {
        Result result = goodsService.selectGoodsList();
        return result;
    }

    /**
     * 获取某个商品的全部信息
     *
     * @param gid
     * @return
     */
    @GetMapping("/getGoodsDetailByGid/{gid}")
    public Result getGoodsDetailByGid(@PathVariable Long gid) {
        Result result = goodsService.selectGoodsDetailByGid(gid);
        return result;
    }


}
