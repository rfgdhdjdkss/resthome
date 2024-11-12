package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.pojo.Bedroom;
import com.jinyang.resthome.pojo.vo.BedRoomVo;
import com.jinyang.resthome.service.BedroomService;
import com.jinyang.resthome.mapper.BedroomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author jinyang
* @description 针对表【bedroom】的数据库操作Service实现
* @createDate 2024-10-27 17:05:50
*/
@Service
public class BedroomServiceImpl extends ServiceImpl<BedroomMapper, Bedroom>
    implements BedroomService{
    @Autowired
    private BedroomMapper bedroomMapper;

    /**
     * 获取所有房间床位信息业务实现代码
     * @return
     */
    @Override
    public List<BedRoomVo> selectBedInfo() {
        List<BedRoomVo> list = bedroomMapper.selectBedInfo();
        return list;
    }
}




