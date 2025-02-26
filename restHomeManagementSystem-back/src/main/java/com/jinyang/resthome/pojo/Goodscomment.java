package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName goodsComment
 */
@TableName(value ="goodsComment")
@Data
public class Goodscomment implements Serializable {
    /**
     * 商品评论唯一标识
     */
    @TableId(value = "goodsCommentId", type = IdType.AUTO)
    private Long goodsCommentId;

    /**
     * 哪个用户的评论
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 商品评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 哪个商品的评论
     */
    @TableField(value = "gid")
    private Long gid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Goodscomment other = (Goodscomment) that;
        return (this.getGoodsCommentId() == null ? other.getGoodsCommentId() == null : this.getGoodsCommentId().equals(other.getGoodsCommentId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGoodsCommentId() == null) ? 0 : getGoodsCommentId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsCommentId=").append(goodsCommentId);
        sb.append(", uid=").append(uid);
        sb.append(", content=").append(content);
        sb.append(", gid=").append(gid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}