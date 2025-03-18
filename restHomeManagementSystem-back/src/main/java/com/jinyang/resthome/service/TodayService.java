package com.jinyang.resthome.service;

import com.jinyang.resthome.pojo.Today;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.Date;

/**
* @author jinyang
* @description 针对表【today】的数据库操作Service
* @createDate 2025-03-18 22:07:14
*/
public interface TodayService extends IService<Today> {

    Today getTodayByDate(Date currentDate);

    void insertToday(Today newToday);

    void updateToday(Today todayRecord);
}
