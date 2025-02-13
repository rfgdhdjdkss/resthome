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
 * @TableName healthRecord
 */
@TableName(value ="healthRecord")
@Data
public class Healthrecord implements Serializable {
    /**
     * 健康记录唯一标识
     */
    @TableId(value = "hrId", type = IdType.AUTO)
    private Long hrId;

    /**
     * 哪个老人的记录
     */
    @TableField(value = "eid")
    private Long eid;

    /**
     * 记录类型
     */
    @TableField(value = "content")
    private String content;

    /**
     * 记录时间
     */
    @TableField(value = "time")
    private Date time;

    /**
     * 记录类型
     */
    @TableField(value = "type")
    private String type;

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
        Healthrecord other = (Healthrecord) that;
        return (this.getHrId() == null ? other.getHrId() == null : this.getHrId().equals(other.getHrId()))
            && (this.getEid() == null ? other.getEid() == null : this.getEid().equals(other.getEid()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHrId() == null) ? 0 : getHrId().hashCode());
        result = prime * result + ((getEid() == null) ? 0 : getEid().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hrId=").append(hrId);
        sb.append(", eid=").append(eid);
        sb.append(", content=").append(content);
        sb.append(", time=").append(time);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}