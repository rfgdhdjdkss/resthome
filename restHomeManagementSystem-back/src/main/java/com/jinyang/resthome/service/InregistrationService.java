package com.jinyang.resthome.service;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Inregistration;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinyang
* @description 针对表【inRegistration】的数据库操作Service
* @createDate 2024-11-13 20:42:32
*/
public interface InregistrationService extends IService<Inregistration> {

    Result saveInregistration(Inregistration inregistration);
}
