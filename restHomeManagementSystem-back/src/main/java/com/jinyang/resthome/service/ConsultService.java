package com.jinyang.resthome.service;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Consult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【consult】的数据库操作Service
 * @createDate 2024-10-13 19:59:20
 */
public interface ConsultService extends IService<Consult> {
    //新增聊天业务
    public Result addChat(Consult consult);

    //获取某个uid的所有聊天业务
    public Result getChat(long uid);

    //管理员或员工获取的所有用户发送的咨询（每个用户为一个咨询）业务
    public Result getConsultCollection();

    //更新某个用户的咨询是否已经回复业务
    public Result updateIsReply(Long whichUidConsult);
}
