package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.pojo.Elderly;
import com.jinyang.resthome.pojo.dto.ElderlyBalanceUpdateRequest;
import com.jinyang.resthome.service.ElderlyService;
import com.jinyang.resthome.mapper.ElderlyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【elderly】的数据库操作Service实现
 * @createDate 2024-10-02 20:32:09
 */
@Service
public class ElderlyServiceImpl extends ServiceImpl<ElderlyMapper, Elderly>
        implements ElderlyService {

    @Autowired
    private ElderlyMapper elderlyMapper;

    /**
     * 新增老人信息业务实现代码
     *
     * @param elderly
     * @return
     */
    @Override
    public int addNewElderly(Elderly elderly) {
        int result = elderlyMapper.insert(elderly);
        return result;
    }

    /**
     * 根据uid获取当前用户预定成功的老人信息业务实现代码
     *
     * @param uid
     * @return
     */
    @Override
    public List<Elderly> selectAllElderly(Long uid) {
        QueryWrapper<Elderly> queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Elderly> elderlyList = elderlyMapper.selectList(queryWrapper);
        return elderlyList;
    }

    /**
     * 搜索框模糊查询老人信息业务实现代码
     *
     * @param searchValue
     * @return
     */
    @Override
    public List<Elderly> selectForSearch(String searchValue) {
        QueryWrapper<Elderly> queryWrapper = new QueryWrapper();
        queryWrapper.like("elderlyName", searchValue);
        List<Elderly> elderlyList = elderlyMapper.selectList(queryWrapper);
        return elderlyList;
    }

    /**
     * 为老人账户充值余额业务实现代码
     *
     * @return
     */
    @Override
    public int rechargeElderlyBalance(long eid, Double money) {
        Elderly elderly = elderlyMapper.selectById(eid);
        UpdateWrapper<Elderly> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("eid", eid);
        updateWrapper.set("balance", money + elderly.getBalance());
        int update = elderlyMapper.update(updateWrapper);
        return update;
    }

}




