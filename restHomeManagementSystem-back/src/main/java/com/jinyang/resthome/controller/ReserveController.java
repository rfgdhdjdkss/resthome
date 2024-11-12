package com.jinyang.resthome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Reserve;
import com.jinyang.resthome.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 针对预定业务的controller
 * @ClassName: ReserveController
 * @Author: jinyang
 * @Date: 2024/10/22 15:54
 * @Version: 1.0
 */
@RestController
@RequestMapping("/reserve")
public class ReserveController {
    @Autowired
    private ReserveService reserveService;

    /**
     * 新增预定
     *
     * @param reserve
     * @return
     */
    @PostMapping("/addReserve")
    public Result addReserve(@RequestBody Reserve reserve) {
        Result result = reserveService.saveReserve(reserve);
        return result;
    }

    /**
     * 获取所有预定
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/selectAllReserve")
    public Result selectAllReserve(
            @RequestParam(value = "currentPage") long currentPage,
            @RequestParam(value = "pageSize") long pageSize
    ) {
        Page<Reserve> page = new Page<>(currentPage, pageSize);
        Page<Reserve> userPage = reserveService.selectAllReserve(page);
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

    /**
     * 根据ID删除预定信息
     *
     * @param eid
     * @return
     */
    @DeleteMapping("/deleteReserve/{eid}")
    public Result deleteUser(@PathVariable("eid") Long eid) {
        Result result = reserveService.deleteReserveByEid(eid);
        return result;
    }

    /**
     * 根据搜索框模糊查询
     *
     * @param currentPage
     * @param pageSize
     * @param searchValue
     * @return
     */
    @GetMapping("/selectForSearch")
    public Result<Object> selectForSearch(
            @RequestParam(value = "currentPage") long currentPage,
            @RequestParam(value = "pageSize") long pageSize,
            @RequestParam(value = "searchValue") String searchValue
    ) {
        Page<Reserve> page = new Page<>(currentPage, pageSize);
        Page<Reserve> userPage = reserveService.selectBySearchValue(page, searchValue);
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


    /**
     * 根据用户id查找这个用户的所有预定信息
     *
     * @param uid
     * @return
     */
    @GetMapping("/selectReserveByUid/{uid}")
    public Result<List<Reserve>> selectReserveByUid(
            @PathVariable(value = "uid") Long uid
    ) {
        List<Reserve> reserveList = reserveService.selectReserveByUid(uid);
        return Result.ok(reserveList);
    }

    /**
     * 根据老人唯一标识eid查找对应id老人的预定信息
     *
     * @param eid
     * @return
     */
    @GetMapping("/selectReserveByEid/{eid}")
    public Result<Reserve> selectReserveByEid(
            @PathVariable(value = "eid") Long eid
    ) {
        Reserve reserve = reserveService.selectReserveByEid(eid);
        return Result.ok(reserve);
    }

    /**
     * 根据eid更新预定信息
     *
     * @param reserve
     * @return
     */
    @PutMapping("/updateReserveByEid")
    public Result<Object> updateReserveByEid(
            @RequestBody Reserve reserve
    ) {
        int result = reserveService.updateReserveByEid(reserve);
        if (result > 0) {
            return Result.ok(result);
        }
        return null;
    }

    /**
     * 根据eid更新老人是否已经预定
     *
     * @param eid
     * @return
     */
    @PutMapping("/updateReserveIsReserved/{eid}")
    public Result<Object> updateReserveIsReserved(
            @PathVariable(value = "eid") Long eid
    ) {
        Reserve reserve = reserveService.updateReserveIsReserved(eid);
        return Result.ok(reserve);
    }

}
