package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Elderly;
import com.jinyang.resthome.pojo.dto.ElderlyBalanceUpdateRequest;
import com.jinyang.resthome.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println(elderly.toString());
        int result = elderlyService.addNewElderly(elderly);
        return Result.ok(result);
    }

    /**
     * 根据uid获取当前用户预定成功的老人信息
     *
     * @param uid
     * @return
     */
    @GetMapping("/selectAllElderly/{uid}")
    public Result selectAllElderly(@PathVariable("uid") Long uid) {
        List<Elderly> result = elderlyService.selectAllElderly(uid);
        return Result.ok(result);
    }

    /**
     * 搜索框模糊查询老人信息
     * @param searchValue
     * @return
     */
    @GetMapping("/selectForSearch")
    public Result selectForSearch(@RequestParam(value = "searchValue") String searchValue) {
        List<Elderly> elderlyList= elderlyService.selectForSearch(searchValue);
        return Result.ok(elderlyList);
    }

    /**
     * 为老人账户充值余额
     * @param request
     * @return
     */
    @PutMapping("/rechargeElderlyBalance")
    public Result rechargeElderlyBalance(@RequestBody ElderlyBalanceUpdateRequest request) {
        int result= elderlyService.rechargeElderlyBalance(request.getEid(), request.getMoney());
        return Result.ok(result);
    }
}
