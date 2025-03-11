package com.jinyang.resthome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.mapper.ElderlyMapper;
import com.jinyang.resthome.pojo.Elderly;
import com.jinyang.resthome.pojo.Elderlyhealth;
import com.jinyang.resthome.pojo.User;
import com.jinyang.resthome.pojo.vo.elderlyHealthVo;
import com.jinyang.resthome.service.ElderlyService;
import com.jinyang.resthome.service.ElderlyhealthService;
import com.jinyang.resthome.util.IdCardAgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: ElderlyhealthController
 * @Author: jinyang
 * @Date: 2025/2/13 15:21
 * @Version: 1.0
 */
@RestController()
@RequestMapping("/eHealth")
public class ElderlyhealthController {
    @Autowired
    private ElderlyhealthService elderlyhealthService;
    @Autowired
    private ElderlyMapper elderlyMapper;

    @GetMapping("/getHealthList/{uid}")
    public Result getHealthListByUid(@PathVariable("uid") Long uid) {
        Result result = elderlyhealthService.selectHealthListByUid(uid);
        return result;
    }

    @GetMapping("/getHealthDetail/{eid}")
    public Result getHealthDetailByEid(@PathVariable("eid") Long eid) {
        Result result = elderlyhealthService.selectHealthDetailByEid(eid);
        return result;
    }


    @GetMapping("/selectAllElderlyWithHealth")
    public Result<Object> selectAllElderlyWithHealth(
            @RequestParam(value = "currentPage") long currentPage,
            @RequestParam(value = "pageSize") long pageSize
    ) {
        Page<Elderlyhealth> page = new Page<>(currentPage, pageSize);
        Page<Elderlyhealth> elderlyhealthPage = elderlyhealthService.findAllElderlyHealth(page);
        System.out.println(elderlyhealthPage.getRecords());

        // 将Elderlyhealth对象转换为elderlyHealthVo对象
        List<elderlyHealthVo> elderlyHealthVoList = convertToElderlyHealthVoList(elderlyhealthPage.getRecords());

        // 创建一个新的Page对象，用于存储elderlyHealthVo数据
        Page<elderlyHealthVo> elderlyHealthVoPage = new Page<>(currentPage, pageSize, elderlyhealthPage.getTotal());
        elderlyHealthVoPage.setRecords(elderlyHealthVoList);

        Map<String, Object> data = new HashMap<>();
        data.put("records", elderlyHealthVoPage.getRecords());
        data.put("total", elderlyHealthVoPage.getTotal());
        data.put("pages", elderlyHealthVoPage.getPages());
        data.put("pageSize", elderlyHealthVoPage.getSize());
        data.put("currentPage", currentPage);
        data.put("hasNext", elderlyHealthVoPage.hasNext());
        data.put("hasPrevious", elderlyHealthVoPage.hasPrevious());
        return Result.ok(data);
    }

    // 将Elderlyhealth列表转换为elderlyHealthVo列表
    private List<elderlyHealthVo> convertToElderlyHealthVoList(List<Elderlyhealth> elderlyhealthList) {
        List<elderlyHealthVo> result = new ArrayList<>();
        for (Elderlyhealth health : elderlyhealthList) {
            Long hid = health.getHid();
            Long eid = health.getEid();
            Elderly elderly = elderlyMapper.selectById(eid);
            String elderlyName = elderly.getElderlyName();
            String elderlyAge = IdCardAgeCalculator.calculateAge(elderly.getElderlyIdCard());
            String image = elderly.getImage();

            String heartRate = health.getHeartRate();
            String bloodPressure = health.getBloodPressure();
            String temperature = health.getTemperature();
            String bedroom = elderly.getBedroom();
            String status = health.getStatus() == 1 ? "正常" : "需要关注";
            result.add(new elderlyHealthVo(hid, eid, elderlyName, elderlyAge, heartRate, bloodPressure, temperature, bedroom, status, image));
        }
        return result;
    }

    @GetMapping("/selectForSearchHealthManagement")
    public Result<Object> selectForSearch(
            @RequestParam(value = "currentPage") long currentPage,
            @RequestParam(value = "pageSize") long pageSize,
            @RequestParam(value = "searchValue") String searchValue
    ) {
        Page<Elderlyhealth> page = new Page<>(currentPage, pageSize);
        Page<Elderlyhealth> elderlyhealthPage = elderlyhealthService.selectBySearchValue(page, searchValue);

        System.out.println(elderlyhealthPage.getRecords());

        // 将Elderlyhealth对象转换为elderlyHealthVo对象
        List<elderlyHealthVo> elderlyHealthVoList = convertToElderlyHealthVoList(elderlyhealthPage.getRecords());

        // 创建一个新的Page对象，用于存储elderlyHealthVo数据
        Page<elderlyHealthVo> elderlyHealthVoPage = new Page<>(currentPage, pageSize, elderlyhealthPage.getTotal());
        elderlyHealthVoPage.setRecords(elderlyHealthVoList);

        Map<String, Object> data = new HashMap<>();
        data.put("records", elderlyHealthVoPage.getRecords());
        data.put("total", elderlyHealthVoPage.getTotal());
        data.put("pages", elderlyHealthVoPage.getPages());
        data.put("pageSize", elderlyHealthVoPage.getSize());
        data.put("currentPage", currentPage);
        data.put("hasNext", elderlyHealthVoPage.hasNext());
        data.put("hasPrevious", elderlyHealthVoPage.hasPrevious());
        return Result.ok(data);
    }

    @PutMapping("/updateElderlyHealth")
    public Result updateElderlyHealth(@RequestBody Elderlyhealth elderlyhealth) {
        Result result = elderlyhealthService.updateByEid(elderlyhealth);
        return result;
    }


}
