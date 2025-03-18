package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.pojo.Today;
import com.jinyang.resthome.service.TodayService;
import com.jinyang.resthome.mapper.TodayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
* @author jinyang
* @description 针对表【today】的数据库操作Service实现
* @createDate 2025-03-18 22:07:14
*/
@Service
public class TodayServiceImpl extends ServiceImpl<TodayMapper, Today>
    implements TodayService{

    @Autowired
    private TodayMapper todayMapper;

    @Override
    public Today getTodayByDate(Date date) {
        return todayMapper.selectByDate(date);
    }

    @Override
    public void insertToday(Today today) {
        int result = todayMapper.insert(today);
    }

    @Override
    public void updateToday(Today todayRecord) {
        todayMapper.updateById(todayRecord);
    }
}




