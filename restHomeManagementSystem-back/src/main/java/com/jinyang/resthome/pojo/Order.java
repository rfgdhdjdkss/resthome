package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName order
 */
@TableName(value ="order")
@Data
public class Order implements Serializable {
    /**
     * 商品订单id
     */
    @TableId(value = "oid", type = IdType.AUTO)
    private Long oid;

    /**
     * 用户id
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 订单创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 订单号
     */
    @TableField(value = "order_number")
    private String order_number;

    /**
     * 订单状态
     */
    @TableField(value = "order_status")
    private String order_status;

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
        Order other = (Order) that;
        return (this.getOid() == null ? other.getOid() == null : this.getOid().equals(other.getOid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getOrder_number() == null ? other.getOrder_number() == null : this.getOrder_number().equals(other.getOrder_number()))
            && (this.getOrder_status() == null ? other.getOrder_status() == null : this.getOrder_status().equals(other.getOrder_status()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOid() == null) ? 0 : getOid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getOrder_number() == null) ? 0 : getOrder_number().hashCode());
        result = prime * result + ((getOrder_status() == null) ? 0 : getOrder_status().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oid=").append(oid);
        sb.append(", uid=").append(uid);
        sb.append(", createTime=").append(createTime);
        sb.append(", order_number=").append(order_number);
        sb.append(", order_status=").append(order_status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}