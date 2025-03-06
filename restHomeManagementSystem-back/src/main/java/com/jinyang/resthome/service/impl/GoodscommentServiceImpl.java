package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.mapper.UserMapper;
import com.jinyang.resthome.pojo.Elderly;
import com.jinyang.resthome.pojo.Elderlyhealth;
import com.jinyang.resthome.pojo.Goodscomment;
import com.jinyang.resthome.pojo.User;
import com.jinyang.resthome.pojo.vo.elderlyHealthVo;
import com.jinyang.resthome.pojo.vo.userGoodsCommentVo;
import com.jinyang.resthome.service.GoodscommentService;
import com.jinyang.resthome.mapper.GoodscommentMapper;
import com.jinyang.resthome.util.IdCardAgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jinyang
 * @description 针对表【goodsComment】的数据库操作Service实现
 * @createDate 2025-02-23 18:22:14
 */
@Service
public class GoodscommentServiceImpl extends ServiceImpl<GoodscommentMapper, Goodscomment>
        implements GoodscommentService {
    @Autowired
    private GoodscommentMapper goodscommentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result selectAllComment(Long gid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gid", gid);
        List<Goodscomment> goodscommentList = goodscommentMapper.selectList(queryWrapper);
        if (goodscommentList.isEmpty()) {
            return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
        } else {
            List<Long> uidList = goodscommentList.stream()
                    .map(Goodscomment::getUid)
                    .collect(Collectors.toList());
            List<User> users = userMapper.selectBatchIds(uidList);
            // 创建一个 Map 用于快速查找 Elderly 对象
            java.util.Map<Long, User> userMap = users.stream()
                    .collect(Collectors.toMap(User::getUid, user -> user));

            List<userGoodsCommentVo> result = new ArrayList<>();
            for (Goodscomment goodscomment : goodscommentList) {
                Long uid = goodscomment.getUid();
                Long goodsid = goodscomment.getGid();
                Long goodsCommentId = goodscomment.getGid();
                String nickname = userMap.get(uid).getNickname();
                String headImgUrl = userMap.get(uid).getHeadImgUrl();
                String content = goodscomment.getContent();
                result.add(new userGoodsCommentVo(goodsCommentId, uid, goodsid, content, headImgUrl, nickname));
            }
            return Result.ok(result);
        }
    }

    @Override
    public Result addGoodsComment(Long uid, Long gid, String content) {
        Goodscomment goodscomment = new Goodscomment();
        goodscomment.setUid(uid);
        goodscomment.setGid(gid);
        goodscomment.setContent(content);
        int result = goodscommentMapper.insert(goodscomment);
        if (result == 1) {
            return Result.ok(goodscomment);
        }
        return Result.build(null, ResultCodeEnum.INSERT_FAIL);
    }
}




