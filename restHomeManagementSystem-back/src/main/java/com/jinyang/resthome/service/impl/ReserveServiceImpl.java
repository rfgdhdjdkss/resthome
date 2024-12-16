package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Reserve;
import com.jinyang.resthome.pojo.User;
import com.jinyang.resthome.service.ReserveService;
import com.jinyang.resthome.mapper.ReserveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jinyang
 * @description 针对表【reserve】的数据库操作Service实现
 * @createDate 2024-10-22 15:54:22
 */
@Service
public class ReserveServiceImpl extends ServiceImpl<ReserveMapper, Reserve>
        implements ReserveService {

    @Autowired
    private ReserveMapper reserveMapper;

    /**
     * 新增预定业务实现代码
     *
     * @param reserve
     * @return
     */
    @Override
    public Result saveReserve(Reserve reserve) {
        int result = reserveMapper.insert(reserve);
        return Result.ok(null);
    }

    /**
     * 获取所有预定业务实现代码
     *
     * @param page
     * @return
     */
    @Override
    public Page<Reserve> selectAllReserve(Page<Reserve> page) {
        Page<Reserve> reserves = reserveMapper.selectPage(page, null);
        return reserves;
    }

    /**
     * 根据id删除预定信息业务实现代码
     *
     * @param eid
     * @return
     */
    @Override
    public Result deleteReserveByEid(Long eid) {
        int result = reserveMapper.deleteById(eid);
        return Result.ok(null);
    }

    /**
     * 搜索框模糊查询业务实现代码
     *
     * @param page
     * @param searchValue
     * @return
     */
    @Override
    public Page<Reserve> selectBySearchValue(Page<Reserve> page, String searchValue) {
        LambdaQueryWrapper<Reserve> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Reserve::getElderlyName, searchValue).or().like(Reserve::getBookerName, searchValue);
        Page<Reserve> reserves = reserveMapper.selectPage(page, wrapper);
        return reserves;
    }

    /**
     * 根据老人唯一标识eid查找对应id老人的预定信息业务实现代码
     *
     * @param eid
     * @return
     */
    @Override
    public Reserve selectReserveByEid(Long eid) {
        Reserve reserve = reserveMapper.selectById(eid);
        return reserve;
    }

    /**
     * 根据用户id查找这个用户的所有预定信息业务实现代码
     *
     * @param uid
     * @return
     */
    @Override
    public List<Reserve> selectReserveByUid(Long uid) {
        QueryWrapper<Reserve> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        List<Reserve> reserveList = reserveMapper.selectList(queryWrapper);
        System.out.println(reserveList);
        return reserveList;
    }

    /**
     * 根据eid更新预定信息业务实现代码
     *
     * @param reserve
     * @return
     */
    @Override
    public int updateReserveByEid(Reserve reserve) {
        int result = reserveMapper.updateById(reserve);
        return result;
    }

    /**
     * 根据eid更新老人是否已经预定业务实现代码
     *
     * @param eid
     * @return
     */
    @Override
    public Reserve updateReserveIsReserved(Long eid) {
        UpdateWrapper<Reserve> wrapper = new UpdateWrapper<>();
        wrapper.eq("eid", eid);
        wrapper.set("isReserved", 1);
        int result = reserveMapper.update(wrapper);
        Reserve reserve = reserveMapper.selectById(eid);
        return reserve;
    }
}




