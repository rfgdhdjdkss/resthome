package com.jinyang.resthome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Elderly;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【elderly】的数据库操作Service
 * @createDate 2024-10-02 20:32:09
 */
public interface ElderlyService extends IService<Elderly> {
    //新增老人信息业务
    int addNewElderly(Elderly elderly);

    //根据uid获取当前用户预定成功的老人信息业务
    List<Elderly> selectAllElderlyByUid(Long uid);

    //搜索框模糊查询老人信息业务
    List<Elderly> selectForSearch(String searchValue);

    //为老人账户充值余额业务
    int rechargeElderlyBalance(long eid, Double money);

    Page<Elderly> selectAllElderly(Page<Elderly> page);

    int updateIsCheckined(long eid, Integer isCheckined);

    Page<Elderly> selectBySearchValue(Page<Elderly> page, String searchValue);

    int deleteByEid(Long eid);

    List<Elderly> selectIsCheckinElderlyByUid(Long uid);

    Result checkOutByEid(Long eid);


    Result insertElderlyByUid(Elderly elderly);
}
