package com.jinyang.resthome.pojo.vo;

import lombok.Data;

/**
 * @Description:
 * @ClassName: userGoodsCommentVo
 * @Author: jinyang
 * @Date: 2025/2/23 18:32
 * @Version: 1.0
 */
@Data
public class userGoodsCommentVo {
    private Long goodsCommentId;
    private Long uid;
    private Long gid;
    private String content;
    private String headImgUrl;
    private String nickName;

    public userGoodsCommentVo(Long goodsCommentId, Long uid, Long gid, String content, String headImgUrl, String nickName) {
        this.goodsCommentId = goodsCommentId;
        this.uid = uid;
        this.gid = gid;
        this.content = content;
        this.headImgUrl = headImgUrl;
        this.nickName = nickName;
    }
}
