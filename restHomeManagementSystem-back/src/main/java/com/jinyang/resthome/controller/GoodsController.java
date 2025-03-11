package com.jinyang.resthome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Goods;
import com.jinyang.resthome.pojo.User;
import com.jinyang.resthome.pojo.dto.GetGoodsInfoByGidListRequest;
import com.jinyang.resthome.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/selectAllGoods")
    public Result selectAllGoods(
                                 @RequestParam(value = "currentPage") long currentPage,
                                 @RequestParam(value = "pageSize") long pageSize
    ) {
        Page<Goods> page = new Page<>(currentPage, pageSize);
        Page<Goods> userPage = goodsService.findAllGoods(page);
        System.out.println(userPage.getRecords());
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
    @DeleteMapping("/deleteGoods/{gid}")
    public Result deleteGoods(@PathVariable("gid") Long gid) {
        Result result = goodsService.deleteGoodsByGid(gid);
        return result;
    }

    @GetMapping("/selectForSearch")
    public Result<Object> selectForSearch(
            @RequestParam(value = "currentPage") long currentPage,
            @RequestParam(value = "pageSize") long pageSize,
            @RequestParam(value = "searchValue") String searchValue
    ) {
        Page<Goods> page = new Page<>(currentPage, pageSize);
        Page<Goods> userPage = goodsService.selectBySearchValue(page, searchValue);
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

    @PutMapping("/updateGoods")
    public Result updateGoods(@RequestBody Goods goods) {
        Result result = goodsService.updateGoodsByGid(goods);
        return Result.ok(result);
    }
    @PostMapping("/addGoods")
    public Result addGoods(@RequestBody Goods goods) {
        Result result = goodsService.insertGoods(goods);
        return Result.ok(result);
    }
}
