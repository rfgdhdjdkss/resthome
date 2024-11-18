package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Outregistration;
import com.jinyang.resthome.service.OutregistrationService;
import com.jinyang.resthome.mapper.OutregistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author jinyang
* @description 针对表【outRegistration】的数据库操作Service实现
* @createDate 2024-11-13 20:51:42
*/
@Service
public class OutregistrationServiceImpl extends ServiceImpl<OutregistrationMapper, Outregistration>
    implements OutregistrationService{
@Autowired
private OutregistrationMapper outregistrationMapper;
    @Override
    public Result saveOutregistration(Outregistration outregistration) {
        int result = outregistrationMapper.insert(outregistration);
        return Result.ok(result);
    }
}




