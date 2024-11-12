package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description: 来充值更新余额时用来接收请求数据的DTO类
 * @ClassName: UserBalanceUpdateRequest
 * @Author: jinyang
 * @Date: 2024/10/29 15:33
 * @Version: 1.0
 */
@Data
public class ElderlyBalanceUpdateRequest {
    private long eid;
    private Double money;
}
