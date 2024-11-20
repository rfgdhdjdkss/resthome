package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.pojo.Rooms;
import com.jinyang.resthome.service.RoomsService;
import com.jinyang.resthome.mapper.RoomsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jinyang
 * @description 针对表【rooms】的数据库操作Service实现
 * @createDate 2024-11-20 20:27:58
 */
@Service
public class RoomsServiceImpl extends ServiceImpl<RoomsMapper, Rooms>
        implements RoomsService {
    @Autowired
    private RoomsMapper roomsMapper;

    @Override
    public Integer getSpaceRoomNumber() {
        Integer result= roomsMapper.selectSpaceRoomNumber();
        return result;
    }
}




