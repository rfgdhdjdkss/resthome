package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Consult;
import com.jinyang.resthome.pojo.vo.ConsultUserVo;
import com.jinyang.resthome.service.ConsultService;
import com.jinyang.resthome.mapper.ConsultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jinyang
 * @description 针对表【consult】的数据库操作Service实现
 * @createDate 2024-10-13 19:59:20
 */
@Service
public class ConsultServiceImpl extends ServiceImpl<ConsultMapper, Consult>
        implements ConsultService {

    @Autowired
    private ConsultMapper consultMapper;

    /**
     * 新增聊天业务实现代码
     *
     * @param consult
     * @return
     */
    @Override
    public Result addChat(Consult consult) {
        if (!consult.getChatContent().isEmpty()) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            consult.setSendTime(sdf.format(date.getTime()));
            if (consult.getPermission().equals("普通用户")) {
                consult.setIsUser("true");
                consult.setIsReply("false");
                consult.setWhichUidConsult(consult.getUid());
            } else {
                consult.setIsUser("false");
                consult.setIsReply("true");
                consult.setWhichUidConsult(consult.getWhichUidConsult());
            }
            int result = consultMapper.insert(consult);
            Map<String, Object> data = new HashMap<>();
            data.put("consult", consult);
            return Result.ok(data);
        }
        return null;
    }

    /**
     * 获取某个uid的所有聊天业务实现代码
     *
     * @param uid
     * @return
     */
    @Override
    public Result getChat(long uid) {
        QueryWrapper<Consult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("whichUidConsult", uid);
        List<Consult> chatList = consultMapper.selectList(queryWrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("consults", chatList);
        return Result.ok(data);
    }

    /**
     * 管理员或员工获取的所有用户发送的咨询（每个用户为一个咨询）业务实现代码
     *
     * @return
     */
    @Override
    public Result getConsultCollection() {
        List<ConsultUserVo> consultCollection = consultMapper.selectWhichUidConsult();
        System.out.println(consultCollection);
        Map<String, Object> data = new HashMap<>();
        data.put("consultCollection", consultCollection);
        return Result.ok(data);
    }

    /**
     * 更新某个用户的咨询是否已经回复业务实现代码
     *
     * @param whichUidConsult
     * @return
     */
    @Override
    public Result updateIsReply(Long whichUidConsult) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("whichUidConsult", whichUidConsult);
        updateWrapper.set("isReply", "true");
        int result = consultMapper.update(updateWrapper);
        return null;
    }


}




