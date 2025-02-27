package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.mapper.BedsMapper;
import com.jinyang.resthome.mapper.RoomsMapper;
import com.jinyang.resthome.pojo.Beds;
import com.jinyang.resthome.pojo.Elderly;
import com.jinyang.resthome.pojo.vo.BedRoomVo;
import com.jinyang.resthome.service.ElderlyService;
import com.jinyang.resthome.mapper.ElderlyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private RoomsMapper roomsMapper;
    @Autowired
    private BedsMapper bedsMapper;

    /**
     * 新增老人信息业务实现代码
     *
     * @param elderly
     * @return
     */
    @Override
    public int addNewElderly(Elderly elderly) {
        BedRoomVo rooms = roomsMapper.selectForBedRoom(elderly.getRoomType());
        elderly.setBedroom(rooms.getRoomNumber() + "-" + rooms.getBedNumber());
        UpdateWrapper<Beds> bedsQueryWrapper = new UpdateWrapper<>();
        bedsQueryWrapper.eq("bed_id", rooms.getBedId()).set("is_occupied", 1);
        bedsMapper.update(bedsQueryWrapper);
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
    public List<Elderly> selectAllElderlyByUid(Long uid) {
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

    @Override
    public Page<Elderly> selectAllElderly(Page<Elderly> page) {
        Page<Elderly> elderlyList = elderlyMapper.selectPage(page, null);
        return elderlyList;
    }

    @Override
    public int updateIsCheckined(long eid, Integer isCheckined) {
        UpdateWrapper<Elderly> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("eid", eid);
        updateWrapper.set("isCheckined", isCheckined);
        int update = elderlyMapper.update(updateWrapper);
        return update;
    }

    @Override
    public Page<Elderly> selectBySearchValue(Page<Elderly> page, String searchValue) {
        LambdaQueryWrapper<Elderly> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Elderly::getElderlyName, searchValue);
        Page<Elderly> elderlyList = elderlyMapper.selectPage(page, wrapper);
        return elderlyList;
    }

    @Override
    public int deleteByEid(Long eid) {
        int result = elderlyMapper.deleteById(eid);
        return result;
    }

    @Override
    public List<Elderly> selectIsCheckinElderlyByUid(Long uid) {
        QueryWrapper<Elderly> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("isCheckined", 1);
        List<Elderly> elderlyList = elderlyMapper.selectList(queryWrapper);
        System.out.println(elderlyList);
        return elderlyList;
    }

    @Override
    public Result checkOutByEid(Long eid) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("eid", eid);
        updateWrapper.set("isCheckined", 0);
        int update = elderlyMapper.update(updateWrapper);
        if (update > 0) {
            return Result.ok(update);
        }
        return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
    }



}




