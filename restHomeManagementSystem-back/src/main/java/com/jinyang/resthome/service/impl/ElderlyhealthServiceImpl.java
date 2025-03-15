package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.mapper.ElderlyMapper;
import com.jinyang.resthome.mapper.HealthrecordMapper;
import com.jinyang.resthome.pojo.Elderly;
import com.jinyang.resthome.pojo.Elderlyhealth;
import com.jinyang.resthome.pojo.Healthrecord;
import com.jinyang.resthome.pojo.User;
import com.jinyang.resthome.pojo.vo.elderlyHealthVo;
import com.jinyang.resthome.service.ElderlyhealthService;
import com.jinyang.resthome.mapper.ElderlyhealthMapper;
import com.jinyang.resthome.util.IdCardAgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jinyang
 * @description 针对表【elderlyHealth】的数据库操作Service实现
 * @createDate 2025-02-13 15:11:18
 */
@Service
public class ElderlyhealthServiceImpl extends ServiceImpl<ElderlyhealthMapper, Elderlyhealth>
        implements ElderlyhealthService {
    @Autowired
    private ElderlyhealthMapper elderlyhealthMapper;
    @Autowired
    private ElderlyMapper elderlyMapper;
    @Autowired
    private HealthrecordMapper healthrecordMapper;
    @Autowired
    private HealthrecordMapper healthRecordMapper;

    @Override
    public Result selectHealthListByUid(Long uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Elderlyhealth> healthList = elderlyhealthMapper.selectList(queryWrapper);
        List<Long> eidList = healthList.stream()
                .map(Elderlyhealth::getEid)
                .collect(Collectors.toList());
        List<Elderly> elderlyList = elderlyMapper.selectBatchIds(eidList);

        // 创建一个 Map 用于快速查找 Elderly 对象
        java.util.Map<Long, Elderly> elderlyMap = elderlyList.stream()
                .collect(Collectors.toMap(Elderly::getEid, elderly -> elderly));

        List<elderlyHealthVo> result = new ArrayList<>();
        for (Elderlyhealth health : healthList) {
            Long hid = health.getHid();
            Long eid = health.getEid();
            String elderlyName = elderlyMap.get(eid).getElderlyName();
            String elderlyAge = IdCardAgeCalculator.calculateAge(elderlyMap.get(eid).getElderlyIdCard());
            String image = elderlyMap.get(eid).getImage();

            String heartRate = health.getHeartRate();
            String bloodPressure = health.getBloodPressure();
            String temperature = health.getTemperature();
            String bedroom = elderlyMap.get(eid).getBedroom();
            Integer status = health.getStatus();
            result.add(new elderlyHealthVo(hid, eid, elderlyName, elderlyAge, heartRate, bloodPressure, temperature, bedroom, status, image));
        }

        return Result.ok(result);
    }

    @Override
    public Result selectHealthDetailByEid(Long eid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("eid", eid);
        Elderlyhealth elderlyhealth = elderlyhealthMapper.selectOne(queryWrapper);
        Elderly elderly = elderlyMapper.selectById(eid);
        List healthrecordList = healthrecordMapper.selectList(queryWrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("elderlyhealth", elderlyhealth);
        result.put("elderly", elderly);
        result.put("healthrecordList", healthrecordList);
        return Result.ok(result);
    }

    // 服务层方法
    @Override
    public Page<Elderlyhealth> findAllElderlyHealth(Page<Elderlyhealth> page) {
        QueryWrapper<Elderlyhealth> queryWrapper = new QueryWrapper<>();
        Page<Elderlyhealth> elderlyhealthPage = elderlyhealthMapper.selectPage(page, queryWrapper);

        return elderlyhealthPage;
    }

    @Override
    public Page<Elderlyhealth> selectBySearchValue(Page<Elderlyhealth> page, String searchValue) {
        // 第一步：根据搜索条件查询符合要求的 Elderly 对象的 eid 列表
        LambdaQueryWrapper<Elderly> elderlyWrapper = Wrappers.lambdaQuery(Elderly.class)
                .like(Elderly::getElderlyName, searchValue)
                .or()
                .like(Elderly::getEid, searchValue)
                .or()
                .like(Elderly::getBedroom, searchValue)
                .select(Elderly::getEid);
        List<Long> eidList = elderlyMapper.selectObjs(elderlyWrapper).stream()
                .map(obj -> (Long) obj)
                .collect(Collectors.toList());

        // 第二步：根据 eid 列表查询 Elderlyhealth 数据并分页
        if (eidList.isEmpty()) {
            return new Page<>();
        }
        LambdaQueryWrapper<Elderlyhealth> healthWrapper = Wrappers.lambdaQuery(Elderlyhealth.class)
                .in(Elderlyhealth::getEid, eidList);
        IPage<Elderlyhealth> elderlyhealthPage = elderlyhealthMapper.selectPage(page, healthWrapper);
        return (Page<Elderlyhealth>) elderlyhealthPage;
    }

    @Override
    public Result updateByEid(Elderlyhealth elderlyhealth) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("eid", elderlyhealth.getEid());
        Elderlyhealth elderlyhealth1 = elderlyhealthMapper.selectOne(queryWrapper);
        if (!elderlyhealth.getBloodPressure().equals(elderlyhealth1.getBloodPressure())) {
            Healthrecord healthrecord = new Healthrecord();
            healthrecord.setEid(elderlyhealth.getEid());
            healthrecord.setContent("进行了血压检测");
            healthrecord.setTime(new Date());
            healthrecord.setType("success");
            healthRecordMapper.insert(healthrecord);
        }
        if (!elderlyhealth.getOxygen().equals(elderlyhealth1.getOxygen())) {
            Healthrecord healthrecord = new Healthrecord();
            healthrecord.setEid(elderlyhealth.getEid());
            healthrecord.setContent("进行了血氧检测");
            healthrecord.setTime(new Date());
            healthrecord.setType("primary");
            healthRecordMapper.insert(healthrecord);

        }
        if (!elderlyhealth.getTemperature().equals(elderlyhealth1.getTemperature())) {
            Healthrecord healthrecord = new Healthrecord();
            healthrecord.setEid(elderlyhealth.getEid());
            healthrecord.setContent("进行了体温检测");
            healthrecord.setTime(new Date());
            healthrecord.setType("info");
            healthRecordMapper.insert(healthrecord);
        }
        if (!elderlyhealth.getHeartRate().equals(elderlyhealth1.getHeartRate())) {
            Healthrecord healthrecord = new Healthrecord();
            healthrecord.setEid(elderlyhealth.getEid());
            healthrecord.setContent("进行了心率检测");
            healthrecord.setTime(new Date());
            healthrecord.setType("warning");
            healthRecordMapper.insert(healthrecord);
        }


        UpdateWrapper<Elderlyhealth> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("eid", elderlyhealth.getEid());

        int update = elderlyhealthMapper.update(elderlyhealth, updateWrapper);
        if (update != 1) {
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }
        return Result.ok(update);
    }

    @Override
    public Result updateStatus(Long eid, Integer status) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("eid", eid);
        updateWrapper.set("status", status);
        int update = elderlyhealthMapper.update(updateWrapper);
        if (update != 1) {
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }
        return Result.ok(update);
    }

}




