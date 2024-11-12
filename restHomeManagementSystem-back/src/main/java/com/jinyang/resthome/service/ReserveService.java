package com.jinyang.resthome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Reserve;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【reserve】的数据库操作Service
 * @createDate 2024-10-22 15:54:22
 */
public interface ReserveService extends IService<Reserve> {
    //新增预定业务
    public Result saveReserve(Reserve reserve);

    //获取所有预定业务
    public Page<Reserve> selectAllReserve(Page<Reserve> page);

    //根据ID删除预定信息业务
    public Result deleteReserveByEid(Long eid);

    //根据搜索框模糊查询业务
    public Page<Reserve> selectBySearchValue(Page<Reserve> page, String searchValue);

    //根据用户id查找这个用户的所有预定信息业务
    public List<Reserve> selectReserveByUid(Long uid);

    //根据老人唯一标识eid查找对应id老人的预定信息业务
    public Reserve selectReserveByEid(Long eid);


    //根据eid更新预定信息业务
    public int updateReserveByEid(Reserve reserve);

    //根据eid更新老人是否已经预定业务
    public Reserve updateReserveIsReserved(Long eid);
}
