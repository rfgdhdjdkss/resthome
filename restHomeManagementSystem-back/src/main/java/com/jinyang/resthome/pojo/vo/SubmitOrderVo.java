package com.jinyang.resthome.pojo.vo;

import com.jinyang.resthome.pojo.Goods;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description: 支付订单响应类
 * @ClassName: SubmitOrderVo
 * @Author: jinyang
 * @Date: 2025/3/2 15:43
 * @Version: 1.0
 */
@Data
public class SubmitOrderVo {
    private Long oid;
    private Long uid;
    private String orderNo;
    private String orderStatus;
    private Date createTime;
    private List goodsList;

    public SubmitOrderVo(Long oid, Long uid, String orderNo, String orderStatus, Date createTime, List goodsList) {
        this.oid = oid;
        this.uid = uid;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.goodsList = goodsList;
    }
}
