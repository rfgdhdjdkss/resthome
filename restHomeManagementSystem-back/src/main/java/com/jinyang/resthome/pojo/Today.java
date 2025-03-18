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
 * @TableName today
 */
@TableName(value ="today")
@Data
public class Today implements Serializable {
    /**
     * 
     */
    @TableId(value = "todayId", type = IdType.AUTO)
    private Long todayId;

    /**
     * 新增入住
     */
    @TableField(value = "todayNewResidents")
    private Integer todayNewResidents;

    /**
     * 新增合同
     */
    @TableField(value = "todayNewContracts")
    private Integer todayNewContracts;

    /**
     * 今日退住
     */
    @TableField(value = "todayExitRequests")
    private Integer todayExitRequests;

    /**
     * 合同到期
     */
    @TableField(value = "todayExpiringContracts")
    private Integer todayExpiringContracts;

    /**
     * 今日日期
     */
    @TableField(value = "date")
    private Date date;

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
        Today other = (Today) that;
        return (this.getTodayId() == null ? other.getTodayId() == null : this.getTodayId().equals(other.getTodayId()))
            && (this.getTodayNewResidents() == null ? other.getTodayNewResidents() == null : this.getTodayNewResidents().equals(other.getTodayNewResidents()))
            && (this.getTodayNewContracts() == null ? other.getTodayNewContracts() == null : this.getTodayNewContracts().equals(other.getTodayNewContracts()))
            && (this.getTodayExitRequests() == null ? other.getTodayExitRequests() == null : this.getTodayExitRequests().equals(other.getTodayExitRequests()))
            && (this.getTodayExpiringContracts() == null ? other.getTodayExpiringContracts() == null : this.getTodayExpiringContracts().equals(other.getTodayExpiringContracts()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTodayId() == null) ? 0 : getTodayId().hashCode());
        result = prime * result + ((getTodayNewResidents() == null) ? 0 : getTodayNewResidents().hashCode());
        result = prime * result + ((getTodayNewContracts() == null) ? 0 : getTodayNewContracts().hashCode());
        result = prime * result + ((getTodayExitRequests() == null) ? 0 : getTodayExitRequests().hashCode());
        result = prime * result + ((getTodayExpiringContracts() == null) ? 0 : getTodayExpiringContracts().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", todayId=").append(todayId);
        sb.append(", todayNewResidents=").append(todayNewResidents);
        sb.append(", todayNewContracts=").append(todayNewContracts);
        sb.append(", todayExitRequests=").append(todayExitRequests);
        sb.append(", todayExpiringContracts=").append(todayExpiringContracts);
        sb.append(", date=").append(date);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}