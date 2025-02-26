package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName receiveAddress
 */
@TableName(value ="receiveAddress")
@Data
public class Receiveaddress implements Serializable {
    /**
     * 收货地址唯一标识
     */
    @TableId(value = "receiveAddressId", type = IdType.AUTO)
    private Long receiveAddressId;

    /**
     * 哪个用户的收货地址
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 收获人姓名
     */
    @TableField(value = "receiveName")
    private String receiveName;

    /**
     * 收获人手机号
     */
    @TableField(value = "receivePhone")
    private String receivePhone;

    /**
     * 收货地址
     */
    @TableField(value = "receiveAddress")
    private String receiveAddress;

    /**
     * 收获地址标签
     */
    @TableField(value = "receiveTag")
    private String receiveTag;

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
        Receiveaddress other = (Receiveaddress) that;
        return (this.getReceiveAddressId() == null ? other.getReceiveAddressId() == null : this.getReceiveAddressId().equals(other.getReceiveAddressId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getReceiveName() == null ? other.getReceiveName() == null : this.getReceiveName().equals(other.getReceiveName()))
            && (this.getReceivePhone() == null ? other.getReceivePhone() == null : this.getReceivePhone().equals(other.getReceivePhone()))
            && (this.getReceiveAddress() == null ? other.getReceiveAddress() == null : this.getReceiveAddress().equals(other.getReceiveAddress()))
            && (this.getReceiveTag() == null ? other.getReceiveTag() == null : this.getReceiveTag().equals(other.getReceiveTag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getReceiveAddressId() == null) ? 0 : getReceiveAddressId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getReceiveName() == null) ? 0 : getReceiveName().hashCode());
        result = prime * result + ((getReceivePhone() == null) ? 0 : getReceivePhone().hashCode());
        result = prime * result + ((getReceiveAddress() == null) ? 0 : getReceiveAddress().hashCode());
        result = prime * result + ((getReceiveTag() == null) ? 0 : getReceiveTag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", receiveAddressId=").append(receiveAddressId);
        sb.append(", uid=").append(uid);
        sb.append(", receiveName=").append(receiveName);
        sb.append(", receivePhone=").append(receivePhone);
        sb.append(", receiveAddress=").append(receiveAddress);
        sb.append(", receiveTag=").append(receiveTag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}