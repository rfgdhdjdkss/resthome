package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Today;
import com.jinyang.resthome.pojo.dto.TodayRequest;
import com.jinyang.resthome.service.TodayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @Description: 今日数据控制器
 * @ClassName: TodayController
 * @Author: jinyang
 * @Date: 2025/3/18 22:07
 * @Version: 1.0
 */
@RestController
@RequestMapping("today")
public class TodayController {
    @Autowired
    private TodayService todayService;
    private final Random random = new Random();

    @GetMapping("/findToday")
    public Result findToday() {
        Date currentDate = new Date();

        Date todayStart = clearTime(currentDate);

        Today todayRecord = todayService.getTodayByDate(todayStart);

        if (todayRecord == null) {
            // 生成随机数据
            Today newToday = new Today();
            newToday.setDate(todayStart);
            newToday.setTodayNewResidents(random.nextInt(10)); // 新增入住（0-9）
            newToday.setTodayNewContracts(random.nextInt(5));  // 新增合同（0-4）
            newToday.setTodayExitRequests(random.nextInt(3)); // 今日退住（0-2）
            newToday.setTodayExpiringContracts(random.nextInt(7)); // 合同到期（0-6）

            todayService.insertToday(newToday);
            return Result.ok(newToday);
        }
        return Result.ok(todayRecord);
    }

    @PutMapping("/incrementNewResidents")
    public Result incrementTodayNewResidents(@RequestBody TodayRequest request) {

        Date todayStart = clearTime(request.getDate());
        Today todayRecord = todayService.getTodayByDate(todayStart);
        if (todayRecord != null) {
            todayRecord.setTodayNewResidents(todayRecord.getTodayNewResidents() + 1);
            todayService.updateToday(todayRecord);
        } else {
            // 生成随机数据
            Today newToday = new Today();
            newToday.setDate(todayStart);
            newToday.setTodayNewResidents(random.nextInt(10)); // 新增入住（0-9）
            newToday.setTodayNewContracts(random.nextInt(5));  // 新增合同（0-4）
            newToday.setTodayExitRequests(random.nextInt(3)); // 今日退住（0-2）
            newToday.setTodayExpiringContracts(random.nextInt(7)); // 合同到期（0-6）
            todayService.insertToday(newToday);
        }
        return Result.ok(null);
    }
    @PutMapping("/incrementExitRequests")
    public Result incrementTodayExitRequests(@RequestBody TodayRequest request) {

        Date todayStart = clearTime(request.getDate());
        Today todayRecord = todayService.getTodayByDate(todayStart);
        if (todayRecord != null) {
            todayRecord.setTodayExitRequests(todayRecord.getTodayExitRequests() + 1);
            todayService.updateToday(todayRecord);
        } else {
            // 生成随机数据
            Today newToday = new Today();
            newToday.setDate(todayStart);
            newToday.setTodayNewResidents(random.nextInt(10)); // 新增入住（0-9）
            newToday.setTodayNewContracts(random.nextInt(5));  // 新增合同（0-4）
            newToday.setTodayExitRequests(random.nextInt(3)); // 今日退住（0-2）
            newToday.setTodayExpiringContracts(random.nextInt(7)); // 合同到期（0-6）
            todayService.insertToday(newToday);
        }
        return Result.ok(null);
    }
    @PutMapping("/incrementNewContracts")
    public Result incrementTodayNewContracts(@RequestBody TodayRequest request) {

        Date todayStart = clearTime(request.getDate());
        Today todayRecord = todayService.getTodayByDate(todayStart);
        if (todayRecord != null) {
            todayRecord.setTodayNewContracts(todayRecord.getTodayNewContracts() + 1);
            todayService.updateToday(todayRecord);
        } else {
            // 生成随机数据
            Today newToday = new Today();
            newToday.setDate(todayStart);
            newToday.setTodayNewResidents(random.nextInt(10)); // 新增入住（0-9）
            newToday.setTodayNewContracts(random.nextInt(5));  // 新增合同（0-4）
            newToday.setTodayExitRequests(random.nextInt(3)); // 今日退住（0-2）
            newToday.setTodayExpiringContracts(random.nextInt(7)); // 合同到期（0-6）
            todayService.insertToday(newToday);
        }
        return Result.ok(null);
    }
    public static Date clearTime(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date todayStart = calendar.getTime();
        return todayStart;
    }

}