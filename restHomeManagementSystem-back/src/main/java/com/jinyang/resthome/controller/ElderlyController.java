package com.jinyang.resthome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Elderly;
import com.jinyang.resthome.pojo.dto.ElderlyBalanceUpdateRequest;
import com.jinyang.resthome.pojo.dto.ElderlyIsCheckinedUpdateRequest;
import com.jinyang.resthome.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: ElderlyController
 * @Author: jinyang
 * @Date: 2024/10/3 19:44
 * @Version: 1.0
 */
@RestController
@RequestMapping("/elderly")
public class ElderlyController {
    @Autowired
    private ElderlyService elderlyService;

    /**
     * 预定成功后新增老人信息
     *
     * @param elderly
     * @return
     */
    @PostMapping("/addNewElderly")
    public Result addNewElderly(@RequestBody Elderly elderly) {
        int result = elderlyService.addNewElderly(elderly);
        return Result.ok(result);
    }
    /**
     * 根据uid获取当前用户预定成功的老人信息
     *
     * @param uid
     * @return
     */
    @GetMapping("/selectAllElderlyByUid/{uid}")
    public Result selectAllElderlyByUid(@PathVariable("uid") Long uid) {
        List<Elderly> result = elderlyService.selectAllElderlyByUid(uid);
        return Result.ok(result);
    }

    /**
     * 搜索框模糊查询老人信息
     *
     * @param searchValue
     * @return
     */
    @GetMapping("/selectForSearch")
    public Result selectForSearch(@RequestParam(value = "searchValue") String searchValue) {
        List<Elderly> elderlyList = elderlyService.selectForSearch(searchValue);
        return Result.ok(elderlyList);
    }

    /**
     * 为老人账户充值余额
     *
     * @param request
     * @return
     */
    @PutMapping("/rechargeElderlyBalance")
    public Result rechargeElderlyBalance(@RequestBody ElderlyBalanceUpdateRequest request) {
        int result = elderlyService.rechargeElderlyBalance(request.getEid(), request.getMoney());
        return Result.ok(result);
    }

    /**
     * 查找所有成功预定的老人信息
     */
    @GetMapping("/selectAllElderly")
    public Result selectAllElderly(@RequestParam(value = "currentPage") long currentPage,
                                   @RequestParam(value = "pageSize") long pageSize) {
        Page<Elderly> page = new Page<>(currentPage, pageSize);
        Page<Elderly> elderlyPage = elderlyService.selectAllElderly(page);
        Map<String, Object> data = new HashMap<>();
        data.put("records", elderlyPage.getRecords());
        data.put("total", elderlyPage.getTotal());
        data.put("pages", elderlyPage.getPages());
        data.put("pageSize", elderlyPage.getSize());
        data.put("currentPage", currentPage);
        data.put("hasNext", elderlyPage.hasNext());
        data.put("hasPrevious", elderlyPage.hasPrevious());
        return Result.ok(data);
    }

    /**
     * 更新入住状态
     * @param request
     * @return
     */
    @PutMapping("/updateIsCheckined")
    public Result updateIsCheckined(@RequestBody ElderlyIsCheckinedUpdateRequest request) {
        int result = elderlyService.updateIsCheckined(request.getEid(), request.getIsCheckined());
        return Result.ok(result);
    }

    /**
     * 实现模糊查询
     * @param currentPage
     * @param pageSize
     * @param searchValue
     * @return
     */
    @GetMapping("/selectForSearchManagement")
    public Result selectForSearchManagement(@RequestParam(value = "currentPage") long currentPage,
                                            @RequestParam(value = "pageSize") long pageSize,
                                            @RequestParam(value = "searchValue") String searchValue) {
        Page<Elderly> page = new Page<>(currentPage, pageSize);
        Page<Elderly> elderlyPage = elderlyService.selectBySearchValue(page, searchValue);
        Map<String, Object> data = new HashMap<>();
        data.put("records", elderlyPage.getRecords());
        data.put("total", elderlyPage.getTotal());
        data.put("pages", elderlyPage.getPages());
        data.put("pageSize", elderlyPage.getSize());
        data.put("currentPage", currentPage);
        data.put("hasNext", elderlyPage.hasNext());
        data.put("hasPrevious", elderlyPage.hasPrevious());
        return Result.ok(data);
    }

    /**
     * 根据eid删除老人信息
     * @param eid
     * @return
     */
    @DeleteMapping("/delete/{eid}")
    public Result delete(@PathVariable("eid") Long eid) {
        System.out.println(eid+"eid");
        int result = elderlyService.deleteByEid(eid);
        return Result.ok(result);
    }

    /**
     * 查询当前用户下已入住的老人信息
     * @param uid
     * @return
     */
    @GetMapping("/selectIsCheckinElderlyByUid/{uid}")
    public Result selectIsCheckinElderlyByUid(@PathVariable("uid") Long uid) {
        List<Elderly> elderlyList = elderlyService.selectIsCheckinElderlyByUid(uid);
        return Result.ok(elderlyList);
    }

    /**
     * 退住申请
     * @param eid
     * @return
     */
    @PutMapping("/checkOut/{eid}")
    public Result checkOut(@PathVariable("eid") Long eid) {
        Result result= elderlyService.checkOutByEid(eid);
        return result;
    }
    @PostMapping("/addElderlyByUid/{uid}")
    public Result addElderlyByUid(@PathVariable("uid") Long uid, @RequestBody Elderly elderly) {
        elderly.setUid(uid);
        Result result= elderlyService.insertElderlyByUid(elderly);
        return result;
    }


}

