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
 * @TableName inRegistration
 */
@TableName(value ="inRegistration")
@Data
public class Inregistration implements Serializable {
    /**
     * 来访登记id
     */
    @TableId(value = "inId", type = IdType.AUTO)
    private Long inId;

    /**
     * 访问哪位老人姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 访问的老人性别
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 预定人姓名
     */
    @TableField(value = "bookerName")
    private String bookerName;

    /**
     * 来访时间
     */
    @TableField(value = "InTime")
    private Date inTime;

    /**
     * 备注
     */
    @TableField(value = "note")
    private String note;

    /**
     * 哪个用户提交的
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
        Inregistration other = (Inregistration) that;
        return (this.getInId() == null ? other.getInId() == null : this.getInId().equals(other.getInId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getBookerName() == null ? other.getBookerName() == null : this.getBookerName().equals(other.getBookerName()))
            && (this.getInTime() == null ? other.getInTime() == null : this.getInTime().equals(other.getInTime()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInId() == null) ? 0 : getInId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getBookerName() == null) ? 0 : getBookerName().hashCode());
        result = prime * result + ((getInTime() == null) ? 0 : getInTime().hashCode());
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
        sb.append(", inId=").append(inId);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", phone=").append(phone);
        sb.append(", bookerName=").append(bookerName);
        sb.append(", inTime=").append(inTime);
        sb.append(", note=").append(note);
        sb.append(", uid=").append(uid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}