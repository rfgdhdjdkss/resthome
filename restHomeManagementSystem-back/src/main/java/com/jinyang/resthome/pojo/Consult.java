package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName consult
 */
@TableName(value = "consult")
@Data
public class Consult implements Serializable {
    /**
     * 咨询记录id
     */
    @TableId(value = "chatid", type = IdType.AUTO)
    private Long chatid;

    /**
     * 哪个用户发送的咨询记录
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 咨询内容
     */
    @TableField(value = "chatContent")
    private String chatContent;

    /**
     * 发送时间
     */
    @TableField(value = "sendTime")
    private String sendTime;

    /**
     * 是否为普通用户发送的
     */
    @TableField(value = "isUser")
    private String isUser;

    /**
     * 权限
     */
    @TableField(value = "permission")
    private String permission;

    /**
     * 在哪一个用户的咨询下
     */
    @TableField(value = "whichUidConsult")
    private Long whichUidConsult;

    /**
     * 已回复/未回复
     */
    @TableField(value = "isReply")
    private String isReply;

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
        Consult other = (Consult) that;
        return (this.getChatid() == null ? other.getChatid() == null : this.getChatid().equals(other.getChatid()))
                && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
                && (this.getChatContent() == null ? other.getChatContent() == null : this.getChatContent().equals(other.getChatContent())
                && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()))
                && (this.getIsUser() == null ? other.getIsUser() == null : this.getIsUser().equals(other.getIsUser()))
                && (this.getPermission() == null ? other.getPermission() == null : this.getPermission().equals(other.getPermission()))
                && (this.getWhichUidConsult() == null ? other.getWhichUidConsult() == null : this.getWhichUidConsult().equals(other.getWhichUidConsult()))
                && (this.getIsReply() == null ? other.getIsReply() == null : this.getIsReply().equals(other.getIsReply()))

        );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getChatid() == null) ? 0 : getChatid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getChatContent() == null) ? 0 : getChatContent().hashCode());
        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
        result = prime * result + ((getIsUser() == null) ? 0 : getIsUser().hashCode());
        result = prime * result + ((getPermission() == null) ? 0 : getPermission().hashCode());
        result = prime * result + ((getWhichUidConsult() == null) ? 0 : getWhichUidConsult().hashCode());
        result = prime * result + ((getIsReply() == null) ? 0 : getIsReply().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", chatid=").append(chatid);
        sb.append(", uid=").append(uid);
        sb.append(", chatContent=").append(chatContent);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", isUser=").append(isUser);
        sb.append(", permission=").append(permission);
        sb.append(", whichUidConsult=").append(whichUidConsult);
        sb.append(", isReply=").append(isReply);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}