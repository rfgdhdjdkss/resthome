package com.jinyang.resthome.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description: 全部订单
 * @ClassName: AllOrderVo
 * @Author: jinyang
 * @Date: 2025/3/2 18:43
 * @Version: 1.0
 */
@Data
public class AllOrderVo {
    private Long oid;
    private Long uid;
    private String orderNumber;
    private String orderStatus;
    private Date createTime;
    private List goodsList;


    public AllOrderVo(Long oid, Long uid, String orderNumber, String orderStatus, Date createTime, List goodsList) {
        this.oid = oid;
        this.uid = uid;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.goodsList = goodsList;
    }
}
