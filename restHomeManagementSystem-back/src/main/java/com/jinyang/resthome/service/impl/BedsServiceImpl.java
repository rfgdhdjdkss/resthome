package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.pojo.Beds;
import com.jinyang.resthome.service.BedsService;
import com.jinyang.resthome.mapper.BedsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author jinyang
* @description 针对表【beds】的数据库操作Service实现
* @createDate 2024-11-20 19:10:29
*/
@Service
public class BedsServiceImpl extends ServiceImpl<BedsMapper, Beds>
    implements BedsService{
@Autowired
private BedsMapper bedsMapper;
    @Override
    public Integer getSpaceBedNumber() {
        Integer result= bedsMapper.selectSpaceBedNumber();
        return result;
    }
}




