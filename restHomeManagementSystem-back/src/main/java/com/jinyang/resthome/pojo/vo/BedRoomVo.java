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
     * 床位id
     */
    private Long bedId;

    /**
     * 床位类型：单人间/双人间/多人间
     */
    private String type;

    /**
     * 是否已经入住,1:入住，0:未入住
     */
    private Integer isCheckin;

    /**
     * 哪位老人入住
     */
    private Long eid;

    /**
     * 哪个用户选择该床位
     */
    private Long uid;

    /**
     * 床位价格
     */
    private Double bedPrice;

    /**
     * 房间号
     */
    private Integer roomNumber;

    /**
     * 床位号
     */
    private Integer bedNumber;
    /**
     * 床位号
     */
    private Integer spaceBedNumber;
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
        BedRoomVo other = (BedRoomVo) that;
        return (this.getBedId() == null ? other.getBedId() == null : this.getBedId().equals(other.getBedId()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getIsCheckin() == null ? other.getIsCheckin() == null : this.getIsCheckin().equals(other.getIsCheckin()))
                && (this.getEid() == null ? other.getEid() == null : this.getEid().equals(other.getEid()))
                && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
                && (this.getBedPrice() == null ? other.getBedPrice() == null : this.getBedPrice().equals(other.getBedPrice()))
                && (this.getRoomNumber() == null ? other.getRoomNumber() == null : this.getRoomNumber().equals(other.getRoomNumber()))
                && (this.getBedNumber() == null ? other.getBedNumber() == null : this.getBedNumber().equals(other.getBedNumber())
                && (this.getSpaceBedNumber() == null ? other.getSpaceBedNumber() == null : this.getSpaceBedNumber().equals(other.getSpaceBedNumber()))
        );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBedId() == null) ? 0 : getBedId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getIsCheckin() == null) ? 0 : getIsCheckin().hashCode());
        result = prime * result + ((getEid() == null) ? 0 : getEid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getBedPrice() == null) ? 0 : getBedPrice().hashCode());
        result = prime * result + ((getRoomNumber() == null) ? 0 : getRoomNumber().hashCode());
        result = prime * result + ((getBedNumber() == null) ? 0 : getBedNumber().hashCode());
        result = prime * result + ((getSpaceBedNumber() == null) ? 0 : getSpaceBedNumber().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bedId=").append(bedId);
        sb.append(", type=").append(type);
        sb.append(", isCheckin=").append(isCheckin);
        sb.append(", eid=").append(eid);
        sb.append(", uid=").append(uid);
        sb.append(", bedPrice=").append(bedPrice);
        sb.append(", roomNumber=").append(roomNumber);
        sb.append(", bedNumber=").append(bedNumber);
        sb.append(", spaceBedNumber=").append(spaceBedNumber);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
