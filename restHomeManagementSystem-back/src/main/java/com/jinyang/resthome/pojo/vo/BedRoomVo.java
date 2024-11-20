package com.jinyang.resthome.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jinyang.resthome.pojo.Bedroom;
import lombok.Data;

/**
 * @Description:
 * @ClassName: BedRoomVo
 * @Author: jinyang
 * @Date: 2024/10/27 19:24
 * @Version: 1.0
 */

@Data
public class BedRoomVo {

    /**
     * 床位类型：单人间/双人间/多人间
     */
    private String roomType;
    /**
     * 床位价格
     */
    private Double price;
    /**
     * 未入住的床位数量
     */
    private Integer spaceBedNumber;
    /**
     * 房间号
     */
    private String roomNumber;
    /**
     * 床位号
     */
    private String bedNumber;
    /**
     * 床位标识
     */
    private Long bedId;

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
        BedRoomVo other = (BedRoomVo) that;
        return (this.getRoomType() == null ? other.getRoomType() == null : this.getRoomType().equals(other.getRoomType()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getSpaceBedNumber() == null ? other.getSpaceBedNumber() == null : this.getSpaceBedNumber().equals(other.getSpaceBedNumber()))
                && (this.getRoomNumber() == null ? other.getRoomNumber() == null : this.getRoomNumber().equals(other.getRoomNumber()))
                && (this.getBedNumber() == null ? other.getBedNumber() == null : this.getBedNumber().equals(other.getBedNumber()))
                && (this.getBedId() == null ? other.getBedId() == null : this.getBedId().equals(other.getBedId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoomType() == null) ? 0 : getRoomType().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getSpaceBedNumber() == null) ? 0 : getSpaceBedNumber().hashCode());
        result = prime * result + ((getRoomNumber() == null) ? 0 : getRoomNumber().hashCode());
        result = prime * result + ((getBedNumber() == null) ? 0 : getBedNumber().hashCode());
        result = prime * result + ((getBedId() == null) ? 0 : getBedId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roomType=").append(roomType);
        sb.append(", price=").append(price);
        sb.append(", spaceBedNumber=").append(spaceBedNumber);
        sb.append(", roomNumber=").append(roomNumber);
        sb.append(", bedNumber=").append(bedNumber);
        sb.append(", bedId=").append(bedId);
        sb.append("]");
        return sb.toString();
    }
}
