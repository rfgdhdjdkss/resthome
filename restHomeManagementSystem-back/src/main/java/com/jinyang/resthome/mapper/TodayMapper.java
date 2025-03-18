package com.jinyang.resthome.mapper;

import com.jinyang.resthome.pojo.Today;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @author jinyang
 * @description 针对表【today】的数据库操作Mapper
 * @createDate 2025-03-18 22:07:14
 * @Entity com.jinyang.resthome.pojo.Today
 */
public interface TodayMapper extends BaseMapper<Today> {

    @Select("SELECT * FROM today WHERE DATE(date) = #{date}")
    Today selectByDate(Date date);

    @Insert("INSERT INTO today (todayNewResidents, todayNewContracts, todayExitRequests, todayExpiringContracts, date) " +
            "VALUES (#{todayNewResidents}, #{todayNewContracts}, #{todayExitRequests}, #{todayExpiringContracts}, #{date})")
    int insert(Today today);
}




