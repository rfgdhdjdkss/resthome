package com.jinyang.resthome.service;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Outregistration;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinyang
* @description 针对表【outRegistration】的数据库操作Service
* @createDate 2024-11-13 20:51:42
*/
public interface OutregistrationService extends IService<Outregistration> {

    Result saveOutregistration(Outregistration outregistration);
}
