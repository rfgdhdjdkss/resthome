package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName beds
 */
@TableName(value ="beds")
@Data
public class Beds implements Serializable {
    /**
     * 床位唯一标识
     */
    @TableId(value = "bed_id", type = IdType.AUTO)
    private Long bed_id;

    /**
     * 房间号
     */
    @TableField(value = "room_number")
    private Long room_number;

    /**
     * 床位号
     */
    @TableField(value = "bed_number")
    private Integer bed_number;

    /**
     * 哪位老人的床位
     */
    @TableField(value = "eid")
    private Long eid;

    /**
     * 是否有人入住，0为否，1为是
     */
    @TableField(value = "is_occupied")
    private Integer is_occupied;

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
        Beds other = (Beds) that;
        return (this.getBed_id() == null ? other.getBed_id() == null : this.getBed_id().equals(other.getBed_id()))
            && (this.getRoom_number() == null ? other.getRoom_number() == null : this.getRoom_number().equals(other.getRoom_number()))
            && (this.getBed_number() == null ? other.getBed_number() == null : this.getBed_number().equals(other.getBed_number()))
            && (this.getEid() == null ? other.getEid() == null : this.getEid().equals(other.getEid()))
            && (this.getIs_occupied() == null ? other.getIs_occupied() == null : this.getIs_occupied().equals(other.getIs_occupied()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBed_id() == null) ? 0 : getBed_id().hashCode());
        result = prime * result + ((getRoom_number() == null) ? 0 : getRoom_number().hashCode());
        result = prime * result + ((getBed_number() == null) ? 0 : getBed_number().hashCode());
        result = prime * result + ((getEid() == null) ? 0 : getEid().hashCode());
        result = prime * result + ((getIs_occupied() == null) ? 0 : getIs_occupied().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bed_id=").append(bed_id);
        sb.append(", room_number=").append(room_number);
        sb.append(", bed_number=").append(bed_number);
        sb.append(", eid=").append(eid);
        sb.append(", is_occupied=").append(is_occupied);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}