package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName dishes
 */
@TableName(value ="dishes")
@Data
public class Dishes implements Serializable {
    /**
     * 菜品id
     */
    @TableId(value = "dishId", type = IdType.AUTO)
    private Long dishId;

    /**
     * 菜品名称
     */
    @TableField(value = "dishName")
    private String dishName;

    /**
     * 菜品价格
     */
    @TableField(value = "dishPrice")
    private Double dishPrice;

    /**
     * 菜品数量
     */
    @TableField(value = "dishQuantity")
    private Integer dishQuantity;

    /**
     * 菜品图片
     */
    @TableField(value = "dishImg")
    private String dishImg;

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
        Dishes other = (Dishes) that;
        return (this.getDishId() == null ? other.getDishId() == null : this.getDishId().equals(other.getDishId()))
            && (this.getDishName() == null ? other.getDishName() == null : this.getDishName().equals(other.getDishName()))
            && (this.getDishPrice() == null ? other.getDishPrice() == null : this.getDishPrice().equals(other.getDishPrice()))
            && (this.getDishQuantity() == null ? other.getDishQuantity() == null : this.getDishQuantity().equals(other.getDishQuantity()))
            && (this.getDishImg() == null ? other.getDishImg() == null : this.getDishImg().equals(other.getDishImg()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDishId() == null) ? 0 : getDishId().hashCode());
        result = prime * result + ((getDishName() == null) ? 0 : getDishName().hashCode());
        result = prime * result + ((getDishPrice() == null) ? 0 : getDishPrice().hashCode());
        result = prime * result + ((getDishQuantity() == null) ? 0 : getDishQuantity().hashCode());
        result = prime * result + ((getDishImg() == null) ? 0 : getDishImg().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dishId=").append(dishId);
        sb.append(", dishName=").append(dishName);
        sb.append(", dishPrice=").append(dishPrice);
        sb.append(", dishQuantity=").append(dishQuantity);
        sb.append(", dishImg=").append(dishImg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}