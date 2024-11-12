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
 * @TableName transactionRecord
 */
@TableName(value ="transactionRecord")
@Data
public class TransactionRecord implements Serializable {
    /**
     * 交易记录id
     */
    @TableId(value = "transactionId", type = IdType.AUTO)
    private Long transactionId;

    /**
     * 交易金额 
     */
    @TableField(value = "transactionMoney")
    private String transactionMoney;

    /**
     * 交易方式
     */
    @TableField(value = "transactionType")
    private String transactionType;

    /**
     * 交易账户
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 交易时间
     */
    @TableField(value = "transactionTime")
    private String transactionTime;

    /**
     * 收入还是支出,1为收入，0为支出
     */
    @TableField(value = "inOrOut")
    private Integer inOrOut;

    /**
     * 交易说明
     */
    @TableField(value = "description")
    private String description;

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
        TransactionRecord other = (TransactionRecord) that;
        return (this.getTransactionId() == null ? other.getTransactionId() == null : this.getTransactionId().equals(other.getTransactionId()))
            && (this.getTransactionMoney() == null ? other.getTransactionMoney() == null : this.getTransactionMoney().equals(other.getTransactionMoney()))
            && (this.getTransactionType() == null ? other.getTransactionType() == null : this.getTransactionType().equals(other.getTransactionType()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getTransactionTime() == null ? other.getTransactionTime() == null : this.getTransactionTime().equals(other.getTransactionTime()))
            && (this.getInOrOut() == null ? other.getInOrOut() == null : this.getInOrOut().equals(other.getInOrOut()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTransactionId() == null) ? 0 : getTransactionId().hashCode());
        result = prime * result + ((getTransactionMoney() == null) ? 0 : getTransactionMoney().hashCode());
        result = prime * result + ((getTransactionType() == null) ? 0 : getTransactionType().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getTransactionTime() == null) ? 0 : getTransactionTime().hashCode());
        result = prime * result + ((getInOrOut() == null) ? 0 : getInOrOut().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", transactionId=").append(transactionId);
        sb.append(", transactionMoney=").append(transactionMoney);
        sb.append(", transactionType=").append(transactionType);
        sb.append(", uid=").append(uid);
        sb.append(", transactionTime=").append(transactionTime);
        sb.append(", inOrOut=").append(inOrOut);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}