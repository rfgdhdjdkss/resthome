package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName goods_order
 */
@TableName(value ="goods_order")
@Data
public class GoodsOrder implements Serializable {
    /**
     * 关联商品和订单记录的唯一标识符，自增主键
     */
    @TableId(value = "goodsOrderid")
    private Long goodsOrderid;

    /**
     * 订单id
     */
    @TableField(value = "oid")
    private Long oid;

    /**
     * 商品id
     */
    @TableField(value = "gid")
    private Long gid;

    /**
     * 数量
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
        GoodsOrder other = (GoodsOrder) that;
        return (this.getGoodsOrderid() == null ? other.getGoodsOrderid() == null : this.getGoodsOrderid().equals(other.getGoodsOrderid()))
            && (this.getOid() == null ? other.getOid() == null : this.getOid().equals(other.getOid()))
            && (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGoodsOrderid() == null) ? 0 : getGoodsOrderid().hashCode());
        result = prime * result + ((getOid() == null) ? 0 : getOid().hashCode());
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsOrderid=").append(goodsOrderid);
        sb.append(", oid=").append(oid);
        sb.append(", gid=").append(gid);
        sb.append(", quantity=").append(quantity);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}