package com.jinyang.resthome.service;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Goodscomment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author jinyang
 * @description 针对表【goodsComment】的数据库操作Service
 * @createDate 2025-02-23 18:22:14
 */
public interface GoodscommentService extends IService<Goodscomment> {

    Result selectAllComment(Long gid);

    Result addGoodsComment(Long uid, Long gid, String content);
}
