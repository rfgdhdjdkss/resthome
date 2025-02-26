package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * 商品唯一标识
     */
    @TableId(value = "gid", type = IdType.AUTO)
    private Long gid;

    /**
     * 商品名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 商品描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 商品价格
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 商品图片
     */
    @TableField(value = "image")
    private String image;

    /**
     * 商品数量
     */
    @TableField(value = "quantity")
    private Integer quantity;

    /**
     * 商品折扣
     */
    @TableField(value = "discount")
    private String discount;

    /**
     * 商品售后
     */
    @TableField(value = "afterSale")
    private String afterSale;

    /**
     * 商品服务
     */
    @TableField(value = "service")
    private String service;

    /**
     * 商品发货地点/运费
     */
    @TableField(value = "deliver")
    private String deliver;

    /**
     * 运费
     */
    @TableField(value = "fee")
    private String fee;

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
        Goods other = (Goods) that;
        return (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getImage() == null ? other.getImage() == null : this.getImage().equals(other.getImage()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getAfterSale() == null ? other.getAfterSale() == null : this.getAfterSale().equals(other.getAfterSale()))
            && (this.getService() == null ? other.getService() == null : this.getService().equals(other.getService()))
            && (this.getDeliver() == null ? other.getDeliver() == null : this.getDeliver().equals(other.getDeliver()))
            && (this.getFee() == null ? other.getFee() == null : this.getFee().equals(other.getFee()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getImage() == null) ? 0 : getImage().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getAfterSale() == null) ? 0 : getAfterSale().hashCode());
        result = prime * result + ((getService() == null) ? 0 : getService().hashCode());
        result = prime * result + ((getDeliver() == null) ? 0 : getDeliver().hashCode());
        result = prime * result + ((getFee() == null) ? 0 : getFee().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", gid=").append(gid);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", price=").append(price);
        sb.append(", image=").append(image);
        sb.append(", quantity=").append(quantity);
        sb.append(", discount=").append(discount);
        sb.append(", afterSale=").append(afterSale);
        sb.append(", service=").append(service);
        sb.append(", deliver=").append(deliver);
        sb.append(", fee=").append(fee);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}