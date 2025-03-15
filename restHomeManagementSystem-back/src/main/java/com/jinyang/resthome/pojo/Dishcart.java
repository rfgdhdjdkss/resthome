package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName dishCart
 */
@TableName(value ="dishCart")
@Data
public class Dishcart implements Serializable {
    /**
     * 餐品购物车
     */
    @TableId(value = "dishCartId")
    private Long dishCartId;

    /**
     * 用户id
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 餐品id
     */
    @TableField(value = "dishId")
    private Long dishId;

    /**
     * 餐品数量
     */
    @TableField(value = "quantity")
    private Integer quantity;

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
        Dishcart other = (Dishcart) that;
        return (this.getDishCartId() == null ? other.getDishCartId() == null : this.getDishCartId().equals(other.getDishCartId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getDishId() == null ? other.getDishId() == null : this.getDishId().equals(other.getDishId()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDishCartId() == null) ? 0 : getDishCartId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getDishId() == null) ? 0 : getDishId().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dishCartId=").append(dishCartId);
        sb.append(", uid=").append(uid);
        sb.append(", dishId=").append(dishId);
        sb.append(", quantity=").append(quantity);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}