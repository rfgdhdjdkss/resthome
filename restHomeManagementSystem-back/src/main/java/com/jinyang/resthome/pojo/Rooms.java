package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName rooms
 */
@TableName(value ="rooms")
@Data
public class Rooms implements Serializable {
    /**
     * 房间唯一标识
     */
    @TableId(value = "room_id", type = IdType.AUTO)
    private Long room_id;

    /**
     * 楼层
     */
    @TableField(value = "floor")
    private Integer floor;

    /**
     * 房间号
     */
    @TableField(value = "room_number")
    private String room_number;

    /**
     * 房间类型：单人间/双人间/多人间
     */
    @TableField(value = "room_type")
    private String room_type;

    /**
     * 房间价格
     */
    @TableField(value = "price")
    private Double price;

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
        Rooms other = (Rooms) that;
        return (this.getRoom_id() == null ? other.getRoom_id() == null : this.getRoom_id().equals(other.getRoom_id()))
            && (this.getFloor() == null ? other.getFloor() == null : this.getFloor().equals(other.getFloor()))
            && (this.getRoom_number() == null ? other.getRoom_number() == null : this.getRoom_number().equals(other.getRoom_number()))
            && (this.getRoom_type() == null ? other.getRoom_type() == null : this.getRoom_type().equals(other.getRoom_type()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoom_id() == null) ? 0 : getRoom_id().hashCode());
        result = prime * result + ((getFloor() == null) ? 0 : getFloor().hashCode());
        result = prime * result + ((getRoom_number() == null) ? 0 : getRoom_number().hashCode());
        result = prime * result + ((getRoom_type() == null) ? 0 : getRoom_type().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", room_id=").append(room_id);
        sb.append(", floor=").append(floor);
        sb.append(", room_number=").append(room_number);
        sb.append(", room_type=").append(room_type);
        sb.append(", price=").append(price);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}