package com.jinyang.resthome.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.mapper.InregistrationMapper;
import com.jinyang.resthome.mapper.OutregistrationMapper;
import com.jinyang.resthome.pojo.Inregistration;
import com.jinyang.resthome.pojo.Outregistration;
import com.jinyang.resthome.pojo.vo.Application;
import com.jinyang.resthome.service.ApplicationService;
import com.jinyang.resthome.service.OutregistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 针对表【inRegistration、outRegistration】的数据库操作Service实现
 * @ClassName: ApplicationServiceImpl
 * @Author: jinyang
 * @Date: 2024/11/14 19:14
 * @Version: 1.0
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private InregistrationMapper inregistrationMapper;
    @Autowired
    private OutregistrationMapper outregistrationMapper;


    @Override
    public List selectAllApplicationByUid(Long uid) {
        List applications = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Inregistration> inregistrationList = inregistrationMapper.selectList(queryWrapper);
        List<Map<String, Object>> transformedInregistrationList = inregistrationList.stream()
                .map(inregistration -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", inregistration.getInId() + inregistration.getUid().toString() + sdf.format(inregistration.getInTime()).substring(8, 13));
                    map.put("name", inregistration.getName());
                    map.put("sex", inregistration.getSex());
                    map.put("phone", inregistration.getPhone());
                    map.put("bookerName", inregistration.getBookerName());
                    map.put("time", simpleDateFormat.format(inregistration.getInTime()));
                    map.put("uid", inregistration.getUid());
                    map.put("type", "来访申请");
                    return map;
                })
                .collect(Collectors.toList());
        List<Outregistration> outregistrationList = outregistrationMapper.selectList(queryWrapper);
        List<Map<String, Object>> transformedOutregistrationList = outregistrationList.stream()
                .map(outregistration -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", outregistration.getOutId() + outregistration.getUid().toString() + sdf.format(outregistration.getOutTime()).substring(8, 13));
                    map.put("name", outregistration.getName());
                    map.put("sex", outregistration.getSex());
                    map.put("phone", outregistration.getPhone());
                    map.put("bookerName", outregistration.getBookerName());
                    map.put("time", simpleDateFormat.format(outregistration.getOutTime()) + " -- " + simpleDateFormat.format(outregistration.getReturnTime()));
                    map.put("uid", outregistration.getUid());
                    map.put("type", "外出申请");
                    return map;
                })
                .collect(Collectors.toList());
        applications.addAll(transformedInregistrationList);
        applications.addAll(transformedOutregistrationList);
        return applications;
    }
}
