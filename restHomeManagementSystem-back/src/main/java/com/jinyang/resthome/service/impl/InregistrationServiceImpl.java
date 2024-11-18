package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.mapper.OutregistrationMapper;
import com.jinyang.resthome.pojo.Inregistration;
import com.jinyang.resthome.pojo.Outregistration;
import com.jinyang.resthome.service.InregistrationService;
import com.jinyang.resthome.mapper.InregistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author jinyang
* @description 针对表【inRegistration】的数据库操作Service实现
* @createDate 2024-11-13 20:42:32
*/
@Service
public class InregistrationServiceImpl extends ServiceImpl<InregistrationMapper, Inregistration>
    implements InregistrationService{
    @Autowired
    private InregistrationMapper inregistrationMapper;
    @Override
    public Result saveInregistration(Inregistration inregistration) {
        int result = inregistrationMapper.insert(inregistration);
        return Result.ok(result);
    }
}




