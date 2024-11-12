package com.jinyang.resthome.service;

import com.jinyang.resthome.pojo.Elderly;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinyang.resthome.pojo.Reserve;
import com.jinyang.resthome.pojo.dto.ElderlyBalanceUpdateRequest;

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
    List<Elderly> selectAllElderly(Long uid);

    //搜索框模糊查询老人信息业务
    List<Elderly> selectForSearch(String searchValue);

    //为老人账户充值余额业务
    int rechargeElderlyBalance(long eid, Double money);
}
