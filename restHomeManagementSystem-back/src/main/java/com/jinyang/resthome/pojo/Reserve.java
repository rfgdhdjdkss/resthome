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
 * @TableName reserve
 */
@TableName(value ="reserve")
@Data
public class Reserve implements Serializable {
    /**
     * 老人信息唯一标识
     */
    @TableId(value = "eid", type = IdType.AUTO)
    private Long eid;

    /**
     * 老人姓名
     */
    @TableField(value = "elderlyName")
    private String elderlyName;

    /**
     * 老人性别
     */
    @TableField(value = "elderlySex")
    private String elderlySex;

    /**
     * 老人出生年月
     */
    @TableField(value = "elderlyBirth")
    private Date elderlyBirth;

    /**
     * 老人身份证号码
     */
    @TableField(value = "elderlyIdCard")
    private String elderlyIdCard;

    /**
     * 联系电话
     */
    @TableField(value = "elderlyPhone")
    private String elderlyPhone;

    /**
     * 家庭住址
     */
    @TableField(value = "elderlyAddress")
    private String elderlyAddress;

    /**
     * 健康状况
     */
    @TableField(value = "isHealth")
    private String isHealth;

    /**
     * 预约人姓名
     */
    @TableField(value = "bookerName")
    private String bookerName;

    /**
     * 预计入住时间
     */
    @TableField(value = "bookerTime")
    private Date bookerTime;

    /**
     * 是否自愿入住，0为否，1为是
     */
    @TableField(value = "isVoluntaryOccupancy")
    private Integer isVoluntaryOccupancy;

    /**
     * 是否已经签约，0为未签约，1为已签约
     */
    @TableField(value = "isReserved")
    private Integer isReserved;

    /**
     * 备注
     */
    @TableField(value = "note")
    private String note;

    /**
     * 哪个用户预定入住
     */
    @TableField(value = "uid")
    private Long uid;

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
        Reserve other = (Reserve) that;
        return (this.getEid() == null ? other.getEid() == null : this.getEid().equals(other.getEid()))
            && (this.getElderlyName() == null ? other.getElderlyName() == null : this.getElderlyName().equals(other.getElderlyName()))
            && (this.getElderlySex() == null ? other.getElderlySex() == null : this.getElderlySex().equals(other.getElderlySex()))
            && (this.getElderlyBirth() == null ? other.getElderlyBirth() == null : this.getElderlyBirth().equals(other.getElderlyBirth()))
            && (this.getElderlyIdCard() == null ? other.getElderlyIdCard() == null : this.getElderlyIdCard().equals(other.getElderlyIdCard()))
            && (this.getElderlyPhone() == null ? other.getElderlyPhone() == null : this.getElderlyPhone().equals(other.getElderlyPhone()))
            && (this.getElderlyAddress() == null ? other.getElderlyAddress() == null : this.getElderlyAddress().equals(other.getElderlyAddress()))
            && (this.getIsHealth() == null ? other.getIsHealth() == null : this.getIsHealth().equals(other.getIsHealth()))
            && (this.getBookerName() == null ? other.getBookerName() == null : this.getBookerName().equals(other.getBookerName()))
            && (this.getBookerTime() == null ? other.getBookerTime() == null : this.getBookerTime().equals(other.getBookerTime()))
            && (this.getIsVoluntaryOccupancy() == null ? other.getIsVoluntaryOccupancy() == null : this.getIsVoluntaryOccupancy().equals(other.getIsVoluntaryOccupancy()))
            && (this.getIsReserved() == null ? other.getIsReserved() == null : this.getIsReserved().equals(other.getIsReserved()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEid() == null) ? 0 : getEid().hashCode());
        result = prime * result + ((getElderlyName() == null) ? 0 : getElderlyName().hashCode());
        result = prime * result + ((getElderlySex() == null) ? 0 : getElderlySex().hashCode());
        result = prime * result + ((getElderlyBirth() == null) ? 0 : getElderlyBirth().hashCode());
        result = prime * result + ((getElderlyIdCard() == null) ? 0 : getElderlyIdCard().hashCode());
        result = prime * result + ((getElderlyPhone() == null) ? 0 : getElderlyPhone().hashCode());
        result = prime * result + ((getElderlyAddress() == null) ? 0 : getElderlyAddress().hashCode());
        result = prime * result + ((getIsHealth() == null) ? 0 : getIsHealth().hashCode());
        result = prime * result + ((getBookerName() == null) ? 0 : getBookerName().hashCode());
        result = prime * result + ((getBookerTime() == null) ? 0 : getBookerTime().hashCode());
        result = prime * result + ((getIsVoluntaryOccupancy() == null) ? 0 : getIsVoluntaryOccupancy().hashCode());
        result = prime * result + ((getIsReserved() == null) ? 0 : getIsReserved().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", eid=").append(eid);
        sb.append(", elderlyName=").append(elderlyName);
        sb.append(", elderlySex=").append(elderlySex);
        sb.append(", elderlyBirth=").append(elderlyBirth);
        sb.append(", elderlyIdCard=").append(elderlyIdCard);
        sb.append(", elderlyPhone=").append(elderlyPhone);
        sb.append(", elderlyAddress=").append(elderlyAddress);
        sb.append(", isHealth=").append(isHealth);
        sb.append(", bookerName=").append(bookerName);
        sb.append(", bookerTime=").append(bookerTime);
        sb.append(", isVoluntaryOccupancy=").append(isVoluntaryOccupancy);
        sb.append(", isReserved=").append(isReserved);
        sb.append(", note=").append(note);
        sb.append(", uid=").append(uid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}