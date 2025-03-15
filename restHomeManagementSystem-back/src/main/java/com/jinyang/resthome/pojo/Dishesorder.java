package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName dishesOrder
 */
@TableName(value ="dishesOrder")
@Data
public class Dishesorder implements Serializable {
    /**
     * 
     */
    @TableId(value = "dOId", type = IdType.AUTO)
    private Long dOId;

    /**
     * 
     */
    @TableField(value = "oid")
    private Long oid;

    /**
     * 
     */
    @TableField(value = "dishId")
    private Long dishId;

    /**
     * 
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
        Dishesorder other = (Dishesorder) that;
        return (this.getDOId() == null ? other.getDOId() == null : this.getDOId().equals(other.getDOId()))
            && (this.getOid() == null ? other.getOid() == null : this.getOid().equals(other.getOid()))
            && (this.getDishId() == null ? other.getDishId() == null : this.getDishId().equals(other.getDishId()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDOId() == null) ? 0 : getDOId().hashCode());
        result = prime * result + ((getOid() == null) ? 0 : getOid().hashCode());
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
        sb.append(", dOId=").append(dOId);
        sb.append(", oid=").append(oid);
        sb.append(", dishId=").append(dishId);
        sb.append(", quantity=").append(quantity);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}