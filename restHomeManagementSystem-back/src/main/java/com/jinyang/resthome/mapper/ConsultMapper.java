package com.jinyang.resthome.mapper;

import com.jinyang.resthome.pojo.Consult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinyang.resthome.pojo.vo.ConsultUserVo;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【consult】的数据库操作Mapper
 * @createDate 2024-10-13 19:59:20
 * @Entity com.jinyang.resthome.pojo.Consult
 */
public interface ConsultMapper extends BaseMapper<Consult> {
    //管理员或员工获取的所有用户发送的咨询（每个用户为一个咨询）数据库操作
    public List<ConsultUserVo> selectWhichUidConsult();
}




