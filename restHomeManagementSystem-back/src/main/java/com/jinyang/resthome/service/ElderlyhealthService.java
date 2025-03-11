package com.jinyang.resthome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Elderlyhealth;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jinyang
* @description 针对表【elderlyHealth】的数据库操作Service
* @createDate 2025-02-13 15:11:18
*/
public interface ElderlyhealthService extends IService<Elderlyhealth> {

    Result selectHealthListByUid(Long uid);

    Result selectHealthDetailByEid(Long eid);

    Page<Elderlyhealth> findAllElderlyHealth(Page<Elderlyhealth> page);

    Page<Elderlyhealth> selectBySearchValue(Page<Elderlyhealth> page, String searchValue);

    Result updateByEid(Elderlyhealth elderlyhealth);
}
