package com.jinyang.resthome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName elderlyHealth
 */
@TableName(value ="elderlyHealth")
@Data
public class Elderlyhealth implements Serializable {
    /**
     * 健康检测表id
     */
    @TableId(value = "hid", type = IdType.AUTO)
    private Long hid;

    /**
     * 哪个老人的健康信息
     */
    @TableField(value = "eid")
    private Long eid;

    /**
     * 属于哪个用户的老人
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 心率
     */
    @TableField(value = "heartRate")
    private String heartRate;

    /**
     * 血压
     */
    @TableField(value = "bloodPressure")
    private String bloodPressure;

    /**
     * 体温
     */
    @TableField(value = "temperature")
    private String temperature;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 血氧
     */
    @TableField(value = "oxygen")
    private String oxygen;

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
        Elderlyhealth other = (Elderlyhealth) that;
        return (this.getHid() == null ? other.getHid() == null : this.getHid().equals(other.getHid()))
            && (this.getEid() == null ? other.getEid() == null : this.getEid().equals(other.getEid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getHeartRate() == null ? other.getHeartRate() == null : this.getHeartRate().equals(other.getHeartRate()))
            && (this.getBloodPressure() == null ? other.getBloodPressure() == null : this.getBloodPressure().equals(other.getBloodPressure()))
            && (this.getTemperature() == null ? other.getTemperature() == null : this.getTemperature().equals(other.getTemperature()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getOxygen() == null ? other.getOxygen() == null : this.getOxygen().equals(other.getOxygen()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHid() == null) ? 0 : getHid().hashCode());
        result = prime * result + ((getEid() == null) ? 0 : getEid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getHeartRate() == null) ? 0 : getHeartRate().hashCode());
        result = prime * result + ((getBloodPressure() == null) ? 0 : getBloodPressure().hashCode());
        result = prime * result + ((getTemperature() == null) ? 0 : getTemperature().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOxygen() == null) ? 0 : getOxygen().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hid=").append(hid);
        sb.append(", eid=").append(eid);
        sb.append(", uid=").append(uid);
        sb.append(", heartRate=").append(heartRate);
        sb.append(", bloodPressure=").append(bloodPressure);
        sb.append(", temperature=").append(temperature);
        sb.append(", status=").append(status);
        sb.append(", oxygen=").append(oxygen);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}