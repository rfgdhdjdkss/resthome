package com.jinyang.resthome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinyang.resthome.pojo.Inregistration;
import com.jinyang.resthome.pojo.Outregistration;
import com.jinyang.resthome.pojo.vo.Application;

import java.util.List;

/**
 * @Description: 针对表【inRegistration、outRegistration】的数据库操作Service
 * @ClassName: ApplicationService
 * @Author: jinyang
 * @Date: 2024/11/14 19:11
 * @Version: 1.0
 */
public interface ApplicationService {
    List selectAllApplicationByUid(Long uid);
}
